package com.university.core.controller;

import com.university.common.entity.Enrollment;
import com.university.core.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody Enrollment enrollmentRequest){
        Enrollment newEnrollment = enrollmentService.enroll(enrollmentRequest);
        return new ResponseEntity<>(newEnrollment, HttpStatus.CREATED);

    }

    @PostMapping("/{id}/drop")
    public ResponseEntity<?> drop(@PathVariable int id){
        enrollmentService.dropEnrollment(id);
        return new ResponseEntity<>(
                Map.of("message", "Enrollment dropped successfully."),
                HttpStatus.OK
        );
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> byStudent(@PathVariable int studentId){
        return enrollmentService.getEnrollmentByStudent(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> byCourse(@PathVariable int courseId){
        return enrollmentService.getEnrollmentByCourse(courseId);
    }

}
