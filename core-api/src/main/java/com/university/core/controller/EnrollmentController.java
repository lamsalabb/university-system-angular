package com.university.core.controller;

import com.university.core.dto.mapper.EnrollmentMapper;
import com.university.core.dto.request.CreateEnrollmentRequest;
import com.university.core.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@Valid @RequestBody CreateEnrollmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        EnrollmentMapper.toResponse(
                                enrollmentService.enroll(request)
                        )
                );
    }

    @PostMapping("/{id}/drop")
    public ResponseEntity<?> drop(@PathVariable int id) {enrollmentService.dropEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public List<?> getEnrollmentByStudent(@PathVariable int studentId) {
        return enrollmentService.getEnrollmentByStudent(studentId)
                .stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }

    @GetMapping("/course/{courseId}")
    public List<?> getEnrollmentByCourse(@PathVariable int courseId) {
        return enrollmentService.getEnrollmentByCourse(courseId)
                .stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }
}
