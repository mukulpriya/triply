package com.mukul.triply.config.interceptors;

import com.mukul.triply.config.security.SecurityContext;
import com.mukul.triply.exception.UnauthenticatedException;
import com.mukul.triply.features.user.token.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthNInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || bearerToken.length() < 7) {
            throw new UnauthenticatedException("Invalid or missing Authorization header");
        }
        final String accessToken = bearerToken.substring(7);
        try {
            final Claims claims = tokenService.getClaims(accessToken);
            if (claims.getExpiration() == null || claims.getExpiration().before(new Date())) {
                throw new UnauthenticatedException("Token is expired.");
            }
            SecurityContext.setSecurityContext(claims.get("userId", String.class), claims.get("companyId", String.class), claims.get("roles", ArrayList.class));
            return true;
        } catch (Exception e) {
            throw new UnauthenticatedException(e.getMessage());
        }
    }
}
