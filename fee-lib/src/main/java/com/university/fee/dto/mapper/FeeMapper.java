package com.university.fee.dto.mapper;

import com.university.fee.dto.response.FeeResponse;
import com.university.fee.entity.Fee;

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
