package com.university.common.dto.mapper;

import com.university.common.dto.response.CourseResponse;
import com.university.common.entity.Course;

public final class CourseMapper {

    private CourseMapper() {
    }

    public static CourseResponse toResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .code(course.getCode())
                .credits(course.getCredits())
                .cost(course.getCost())
                .description(course.getDescription())
                .instructorId(course.getInstructor().getId())
                .instructorName(course.getInstructor().getFirstName() + " " + course.getInstructor().getLastName())
                .build();
    }
}
