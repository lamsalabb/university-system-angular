package com.university.core.dto.response;

import com.university.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String email;
    private User.Role role;
    private String firstName;
    private String lastName;
    private boolean isActive;
}
