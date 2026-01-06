package com.university.core.controller;

import com.university.common.entity.Attendance;
import com.university.core.dto.mapper.AttendanceMapper;
import com.university.core.dto.request.MarkAttendanceRequest;
import com.university.core.dto.response.AttendanceResponse;
import com.university.core.dto.response.AttendanceSummaryResponse;
import com.university.core.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    public ResponseEntity<AttendanceResponse> markAttendance(
            @Valid @RequestBody MarkAttendanceRequest attendanceRequest) {

        Attendance attendance = attendanceService.markAttendance(
                attendanceRequest.getEnrollmentId(),
                attendanceRequest.getSessionDate(),
                attendanceRequest.getStatus(),
                attendanceRequest.getRemarks()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AttendanceMapper.toResponse(attendance));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponse>> getByStudent(
            @PathVariable int studentId) {

        List<AttendanceResponse> responses =
                attendanceService.getAttendanceByStudent(studentId)
                        .stream()
                        .map(AttendanceMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }


    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<AttendanceResponse>> getByStudentAndCourse(
            @PathVariable int studentId,
            @PathVariable int courseId) {

        List<AttendanceResponse> responses =
                attendanceService.getAttendanceByStudentAndCourse(studentId, courseId)
                        .stream()
                        .map(AttendanceMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AttendanceResponse>> getByCourseId(
            @PathVariable int courseId) {

        List<AttendanceResponse> responses =
                attendanceService.getAttendanceByCourseId(courseId)
                        .stream()
                        .map(AttendanceMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/student/{attendanceId}/attendance/{status}")
    public ResponseEntity<Void>updateAttendance(
            @PathVariable int attendanceId,
            @PathVariable String status) {

                attendanceService.updateStatus(attendanceId, status);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}/course/{courseId}/summary")
    public ResponseEntity<AttendanceSummaryResponse> getSummary(
            @PathVariable int studentId,
            @PathVariable int courseId) {

        AttendanceService.AttendanceSummary summary =
                attendanceService.getSummaryForStudentInCourse(studentId, courseId);

        return ResponseEntity.ok(
                new AttendanceSummaryResponse(
                        studentId,
                        courseId,
                        summary.totalSessions(),
                        summary.presentCount(),
                        summary.absentCount(),
                        summary.excusedCount(),
                        summary.presentPercent()
                )
        );
    }
}
