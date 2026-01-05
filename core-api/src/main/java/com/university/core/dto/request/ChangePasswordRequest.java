package com.university.core.dto.request;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {}
