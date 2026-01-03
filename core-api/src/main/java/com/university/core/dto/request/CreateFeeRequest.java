package com.university.core.dto.request;

import com.university.common.entity.Fee;
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
