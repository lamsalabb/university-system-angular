package com.university.core.dto.response;

public record FeeSummaryResponse(
        int studentId,
        int outstandingAmount
) {
}
