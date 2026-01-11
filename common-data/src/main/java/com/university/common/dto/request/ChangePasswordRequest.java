package com.university.common.dto.request;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {
}
