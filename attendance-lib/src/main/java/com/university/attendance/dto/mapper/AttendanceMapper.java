package com.university.attendance.dto.mapper;

import com.university.attendance.dto.response.AttendanceResponse;
import com.university.attendance.entity.Attendance;
import com.university.common.dto.response.CourseSummaryResponse;
import com.university.common.dto.response.StudentSummaryResponse;

public final class AttendanceMapper {

    private AttendanceMapper() {
    }

    public static AttendanceResponse toResponse(Attendance attendance) {
        return AttendanceResponse.builder()
                .id(attendance.getId())
                .enrollmentId(attendance.getEnrollment().getId())
                .sessionDate(attendance.getSessionDate())
                .status(attendance.getStatus())
                .remarks(attendance.getRemarks())
                .course(
                        CourseSummaryResponse.builder()
                                .id(attendance.getEnrollment().getCourse().getId())
                                .title(attendance.getEnrollment().getCourse().getTitle())
                                .build()
                )
                .student(
                        StudentSummaryResponse.builder()
                                .id(attendance.getEnrollment().getStudent().getId())
                                .firstName(attendance.getEnrollment().getStudent().getFirstName())
                                .lastName(attendance.getEnrollment().getStudent().getLastName())
                                .build()
                )
                .build();
    }
}
