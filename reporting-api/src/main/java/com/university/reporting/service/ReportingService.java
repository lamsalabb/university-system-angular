package com.university.reporting.service;

import com.university.reporting.contract.dto.ActiveStudentDTO;
import com.university.reporting.contract.dto.AverageGradeDTO;
import com.university.reporting.contract.dto.CourseEnrollmentDTO;
import com.university.reporting.repository.ReportingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingService {

    private final ReportingRepository repository;

    public ReportingService(ReportingRepository repository) {
        this.repository = repository;
    }

    public List<ActiveStudentDTO> activeStudents() {
        return repository.findActiveStudents();
    }

    public List<CourseEnrollmentDTO> courseEnrollment() {
        return repository.enrollmentByCourse();
    }

    public List<AverageGradeDTO> averageGrades() {
        return repository.averageGrades();
    }


}
