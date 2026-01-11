package com.university.attendance.dto.response;

import com.university.attendance.entity.Attendance;
import com.university.common.dto.response.CourseSummaryResponse;
import com.university.common.dto.response.StudentSummaryResponse;
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
