package com.university.attendance.dto.response;

public record AttendanceSummaryResponse(
        Integer studentId,
        int courseId,
        int totalSessions,
        long presentCount,
        long absentCount,
        long excusedCount,
        double presentPercent
) {
}
