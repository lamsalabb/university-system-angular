package com.university.fee.dto.response;

public record FeeSummaryResponse(
        int studentId,
        int outstandingAmount
) {
}
