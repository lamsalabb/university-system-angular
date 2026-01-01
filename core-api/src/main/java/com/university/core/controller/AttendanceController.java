package com.university.core.controller;

import com.university.common.entity.Attendance;
import com.university.core.service.AttendanceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;


    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody Attendance attendanceRequest){
        Attendance attendance = attendanceService.markAttendance(attendanceRequest.getEnrollment().getId(), attendanceRequest.getSessionDate(),attendanceRequest.getStatus(),attendanceRequest.getRemarks());

        return new ResponseEntity<>(attendance, HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public List<Attendance> getByStudent(@PathVariable int studentId){
        return attendanceService.getAttendanceByStudent(studentId);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public List<Attendance> getByStudentAndCourse(@PathVariable int studentId, @PathVariable int courseId){
        return attendanceService.getAttendanceByStudentAndCourse(studentId,courseId);
    }

    @GetMapping("/student/{studentId}/course/{courseId}/summary")
    public ResponseEntity<?> getSummary(@PathVariable int studentId, @PathVariable int courseId){

        AttendanceService.AttendanceSummary summary = attendanceService.getSummaryForStudentInCourse(studentId, courseId);

        return new ResponseEntity<>(
                Map.of(
                        "studentId",studentId,
                        "courseId",courseId,
                        "totalSessions",summary.totalSessions(),
                        "presentCount",summary.presentCount(),
                        "absentCount", summary.absentCount(),
                        "excusedCount", summary.excusedCount(),
                        "presentPercent", summary.presentPercent()
                ),
                HttpStatus.OK
        );

    }
}
