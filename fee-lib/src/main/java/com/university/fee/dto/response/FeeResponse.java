package com.university.fee.dto.response;

import com.university.fee.entity.Fee;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class FeeResponse {

    int id;
    int studentId;
    int amount;
    Fee.Type type;
    boolean paid;
    LocalDate dueDate;
    LocalDate paymentDate;
}
