package com.university.common.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

    @Email
    private String email;

    private String firstName;
    private String lastName;

    @Size(min = 6)
    private String password;

    private Boolean isActive;
}
