package com.university.common.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourseResponse {

    int id;
    String title;
    String code;
    int credits;
    String description;
    int instructorId;
    int cost;
    String instructorName;
}
