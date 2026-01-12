package com.university.common.dto.mapper;

import com.university.common.dto.response.UserResponse;
import com.university.common.entity.User;

public class UserMapper {
    public static UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(),
                u.getEmail(),
                u.getRole(),
                u.getFirstName(),
                u.getLastName(),
                u.getIsActive()
        );
    }
}
