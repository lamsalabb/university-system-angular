package com.university.core.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentSummaryResponse {
    int id;
    String firstName;
    String lastName;
    String email;
}

