package com.mukul.triply.config.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SecurityContext {
    private static final ThreadLocal<String> LOGGED_IN_USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> LOGGED_IN_USER_COMPANY_ID = new ThreadLocal<>();
    private static final ThreadLocal<Set<String>> LOGGED_IN_USER_ROLES = new ThreadLocal<>();

    public static void setSecurityContext(final String userId, final String companyId, final ArrayList<String> roles) {
        LOGGED_IN_USER_ID.set(userId);
        LOGGED_IN_USER_COMPANY_ID.set(companyId);
        LOGGED_IN_USER_ROLES.set(Collections.unmodifiableSet(new HashSet<>(roles)));
    }

    public static String getLoggedInUserId() {
        return LOGGED_IN_USER_ID.get();
    }

    public static String getLoggedInUserCompanyId() {
        return LOGGED_IN_USER_COMPANY_ID.get();
    }

    public static Set<String> getRoles() {
        return LOGGED_IN_USER_ROLES.get();
    }
}
