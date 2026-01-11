package com.university.attendance.dto.request;

import com.university.attendance.entity.Attendance;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MarkAttendanceRequest {

    @NotNull
    private Integer enrollmentId;

    @NotNull
    private LocalDate sessionDate;

    @NotNull
    private Attendance.Status status;

    private String remarks;
}
