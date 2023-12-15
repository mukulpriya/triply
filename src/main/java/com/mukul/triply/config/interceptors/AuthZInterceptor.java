package com.mukul.triply.config.interceptors;

import com.mukul.triply.config.security.HasRole;
import com.mukul.triply.config.security.SecurityContext;
import com.mukul.triply.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Set;

@Component
public class AuthZInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            final HasRole hasRoleAnnotation = handlerMethod.getMethodAnnotation(HasRole.class);
            if (hasRoleAnnotation != null) {
                String[] requiredRoles = hasRoleAnnotation.value();
                if (hasRequiredRoles(requiredRoles)) {
                    return true;
                } else {
                    throw new UnauthorizedException("User doesn't have the required role for this API.");
                }
            }
        }

        return true;
    }

    private boolean hasRequiredRoles(String[] requiredRoles) {
        final Set<String> userRoles = SecurityContext.getRoles();
        return Arrays.stream(requiredRoles).anyMatch(userRoles::contains);
    }
}
