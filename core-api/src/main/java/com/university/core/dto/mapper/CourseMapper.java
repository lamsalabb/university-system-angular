package com.university.core.dto.mapper;

import com.university.common.entity.Course;
import com.university.core.dto.response.CourseResponse;

public final class CourseMapper {

    private CourseMapper() {}

    public static CourseResponse toResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .code(course.getCode())
                .credits(course.getCredits())
                .description(course.getDescription())
                .instructorId(course.getInstructor().getId())
                .build();
    }
}
