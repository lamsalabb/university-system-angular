package com.university.common.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourseSummaryResponse {
    int id;
    String title;
}
