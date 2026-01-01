package com.university.reporting.controller;

import com.university.reporting.contract.dto.ActiveStudentDTO;
import com.university.reporting.contract.dto.AverageGradeDTO;
import com.university.reporting.contract.dto.CourseEnrollmentDTO;
import com.university.reporting.service.ReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final ReportingService service;

    public ReportingController(ReportingService service) {
        this.service = service;
    }

    @GetMapping("/active-students")
    public List<ActiveStudentDTO> activeStudents() {
        return service.activeStudents();
    }

    @GetMapping("/course-enrollment")
    public List<CourseEnrollmentDTO> enrollmentByCourse() {
        return service.courseEnrollment();
    }
    @GetMapping("/average-grades")
    public List<AverageGradeDTO> averageGrades() {
        return service.averageGrades();
    }
}
