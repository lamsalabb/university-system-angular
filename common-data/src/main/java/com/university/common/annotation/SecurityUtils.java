package com.university.common.annotation;

import com.university.common.exception.InvalidUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Integer currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return Integer.parseInt(Objects.requireNonNull(auth.getPrincipal()).toString());
        } else throw new InvalidUserException("Authentication not found for the current user.");
    }
}
