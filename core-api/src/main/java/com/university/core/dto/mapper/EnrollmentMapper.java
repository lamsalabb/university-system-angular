package com.university.core.dto.mapper;

import com.university.common.entity.Enrollment;
import com.university.core.dto.response.CourseSummaryResponse;
import com.university.core.dto.response.EnrollmentResponse;
import com.university.core.dto.response.StudentSummaryResponse;

public class EnrollmentMapper {

    public static EnrollmentResponse toResponse(Enrollment e) {
        return EnrollmentResponse.builder()
                .id(e.getId())
                .semester(e.getSemester())
                .grade(e.getGrade())
                .status(e.getStatus())
                .enrollmentDate(e.getEnrollmentDate())
                .student(
                        StudentSummaryResponse.builder()
                                .id(e.getStudent().getId())
                                .firstName(e.getStudent().getFirstName())
                                .lastName(e.getStudent().getLastName())
                                .email(e.getStudent().getEmail())
                                .build()
                )
                .course(
                        CourseSummaryResponse.builder()
                                .id(e.getCourse().getId())
                                .title(e.getCourse().getTitle())
                                .build()
                )
                .build();
    }
}
