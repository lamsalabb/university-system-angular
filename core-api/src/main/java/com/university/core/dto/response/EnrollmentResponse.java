package com.university.core.dto.response;

import com.university.common.entity.Enrollment;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class EnrollmentResponse {

    int id;
    StudentSummaryResponse student;
    CourseSummaryResponse course;
    String semester;
    String grade;
    Enrollment.Status status;
    LocalDate enrollmentDate;

}
