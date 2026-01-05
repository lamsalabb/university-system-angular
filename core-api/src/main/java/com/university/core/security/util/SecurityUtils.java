package com.university.core.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Integer currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null) {
            return Integer.parseInt(auth.getPrincipal().toString());
        }
        else throw new RuntimeException("Authentication not found.");
    }
}
