package com.university.core.controller;

import com.university.common.dto.mapper.EnrollmentMapper;
import com.university.common.dto.request.CreateEnrollmentRequest;
import com.university.common.dto.response.EnrollmentResponse;
import com.university.core.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

//    @GetMapping
//    public List<EnrollmentResponse> getAllEnrollments() {
//        return enrollmentService.getAllEnrollments()
//                .stream()
//                .map(EnrollmentMapper::toResponse)
//                .toList();
//    }

    @GetMapping
    public ResponseEntity<?> getAllEnrollments(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Page<EnrollmentResponse> enrollments = enrollmentService.getAllEnrollments(pageable).map(EnrollmentMapper::toResponse);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentResponse> enroll(@Valid @RequestBody CreateEnrollmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EnrollmentMapper.toResponse(enrollmentService.enroll(request))
                );
    }

    @PostMapping("/{id}/drop")
    public ResponseEntity<?> drop(@PathVariable int id) {
        enrollmentService.dropEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public List<EnrollmentResponse> getEnrollmentByStudent(@PathVariable int studentId) {
        return enrollmentService.getEnrollmentByStudent(studentId)
                .stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }

    @GetMapping("/course/{courseId}")
    public List<EnrollmentResponse> getEnrollmentByCourse(@PathVariable int courseId) {
        return enrollmentService.getEnrollmentByCourse(courseId)
                .stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<?> getEnrollmentByInstructorId(@PathVariable int id, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<EnrollmentResponse> enrollments = enrollmentService.getEnrollmentsByInstructor(id, pageable)
                .map(EnrollmentMapper::toResponse);

        return ResponseEntity.ok(enrollments);
    }


    @PutMapping("/{id}/{grade}")
    public ResponseEntity<?> updateGrade(@PathVariable Integer id, @PathVariable String grade) {

        enrollmentService.updateGrade(id, grade);
        return ResponseEntity.noContent().build();

    }


}
