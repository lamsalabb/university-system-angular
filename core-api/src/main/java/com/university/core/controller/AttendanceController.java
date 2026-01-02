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
    public ResponseEntity<AttendanceResponse> markAttendance(@Valid @RequestBody MarkAttendanceRequest attendanceRequest){
        Attendance attendance = attendanceService.markAttendance(attendanceRequest.getEnrollmentId(), attendanceRequest.getSessionDate(),attendanceRequest.getStatus(),attendanceRequest.getRemarks());

        return new ResponseEntity<>(AttendanceMapper.toResponse(attendance), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public List<AttendanceResponse> getByStudent(@PathVariable int studentId){
        return attendanceService.getAttendanceByStudent(studentId)
                .stream()
                .map(AttendanceMapper::toResponse)
                .toList();
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public List<AttendanceResponse> getByStudentAndCourse(@PathVariable int studentId, @PathVariable int courseId){
        return attendanceService.getAttendanceByStudentAndCourse(studentId,courseId)
                .stream()
                .map(AttendanceMapper::toResponse)
                .toList();
    }

    @GetMapping("/student/{studentId}/course/{courseId}/summary")
    public AttendanceSummaryResponse getSummary(@PathVariable int studentId, @PathVariable int courseId){

        AttendanceService.AttendanceSummary summary = attendanceService.getSummaryForStudentInCourse(studentId, courseId);

        return new AttendanceSummaryResponse(
                studentId,
                courseId,
                summary.totalSessions(),
                summary.presentCount(),
                summary.absentCount(),
                summary.excusedCount(),
                summary.presentPercent()

        );

    }
}
