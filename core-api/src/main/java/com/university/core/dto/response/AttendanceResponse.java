package com.university.core.dto.response;

import com.university.common.entity.Attendance;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class AttendanceResponse {

    int id;
    int enrollmentId;
    LocalDate sessionDate;
    Attendance.Status status;
    String remarks;
    CourseSummaryResponse course;
    StudentSummaryResponse student;
}
