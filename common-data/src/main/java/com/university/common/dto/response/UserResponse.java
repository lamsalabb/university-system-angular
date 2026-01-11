package com.university.common.dto.response;

import com.university.common.entity.User;

public record UserResponse(int id, String email, User.Role role, String firstName, String lastName, boolean active) {
}
