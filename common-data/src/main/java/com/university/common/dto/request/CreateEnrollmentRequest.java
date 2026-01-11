package com.university.common.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateEnrollmentRequest {

    @NotNull
    private Integer studentId;

    @NotNull
    private Integer courseId;

    @NotNull
    private String semester;
}
