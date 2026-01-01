package com.university.core.controller;

import com.university.core.client.ReportingClient;
import com.university.reporting.contract.dto.ActiveStudentDTO;
import com.university.reporting.contract.dto.AverageGradeDTO;
import com.university.reporting.contract.dto.CourseEnrollmentDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingProxyController {

    private final ReportingClient reportingClient;

    public ReportingProxyController(ReportingClient reportingClient) {
        this.reportingClient = reportingClient;
    }

    @GetMapping("/active-students")
    public List<ActiveStudentDTO> activeStudents() {
        return reportingClient.getActiveStudents();
    }

    @GetMapping("/average-grades")
    public List<AverageGradeDTO> averageGrades() {
        return reportingClient.getAverageGrades();
    }

    @GetMapping("/course-enrollment")
    public List<CourseEnrollmentDTO> courseEnrollments() {
        return reportingClient.getCourseEnrollmentStats();
    }
}
