package com.university.core.dto.mapper;

import com.university.common.entity.Attendance;
import com.university.core.dto.response.AttendanceResponse;

public final class AttendanceMapper {

    private AttendanceMapper() {}

    public static AttendanceResponse toResponse(Attendance attendance) {
        return AttendanceResponse.builder()
                .id(attendance.getId())
                .enrollmentId(attendance.getEnrollment().getId())
                .sessionDate(attendance.getSessionDate())
                .status(attendance.getStatus())
                .remarks(attendance.getRemarks())
                .build();
    }
}
