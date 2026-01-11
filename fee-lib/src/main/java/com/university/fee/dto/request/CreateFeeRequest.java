package com.university.fee.dto.request;

import com.university.fee.entity.Fee;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateFeeRequest {

    @NotNull
    private Integer studentId;

    @NotNull
    private Integer amount;

    @NotNull
    private Fee.Type type;

    @NotNull
    private LocalDate dueDate;
}
