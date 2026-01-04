package com.university.core.dto.mapper;

import com.university.common.entity.Enrollment;
import com.university.core.dto.response.EnrollmentResponse;

public final class EnrollmentMapper {

    private EnrollmentMapper() {
    }

    public static EnrollmentResponse toResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudent().getId())
                .courseId(enrollment.getCourse().getId())
                .semester(enrollment.getSemester())
                .grade(enrollment.getGrade())
                .status(enrollment.getStatus())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .build();
    }
}
