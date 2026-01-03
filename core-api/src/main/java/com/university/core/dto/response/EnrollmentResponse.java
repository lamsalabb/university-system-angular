package com.university.core.dto.response;

import com.university.common.entity.Enrollment;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class EnrollmentResponse {

    int id;
    int studentId;
    int courseId;
    String semester;
    Enrollment.Status status;
    LocalDate enrollmentDate;
}
