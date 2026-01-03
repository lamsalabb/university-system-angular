package com.university.core.dto.mapper;

import com.university.common.entity.Fee;
import com.university.core.dto.response.FeeResponse;

public final class FeeMapper {

    private FeeMapper() {
    }

    public static FeeResponse toResponse(Fee fee) {
        return FeeResponse.builder()
                .id(fee.getId())
                .studentId(fee.getStudent().getId())
                .amount(fee.getAmount())
                .type(fee.getType())
                .paid(fee.isPaid())
                .dueDate(fee.getDueDate())
                .paymentDate(fee.getPaymentDate())
                .build();
    }
}
