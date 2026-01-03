package com.university.core.client;

import com.university.reporting.contract.dto.ActiveStudentDTO;
import com.university.reporting.contract.dto.AverageGradeDTO;
import com.university.reporting.contract.dto.CourseEnrollmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "reporting-api",
        url = "${reporting.api.url}")
public interface ReportingClient {

    @GetMapping("/api/reports/active-students")
    List<ActiveStudentDTO> getActiveStudents();

    @GetMapping("/api/reports/course-enrollment")
    List<CourseEnrollmentDTO> getCourseEnrollmentStats();

    @GetMapping("/api/reports/average-grades")
    List<AverageGradeDTO> getAverageGrades();
}
