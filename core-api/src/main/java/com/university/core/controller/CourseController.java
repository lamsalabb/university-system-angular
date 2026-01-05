package com.university.core.controller;

import com.university.core.dto.mapper.CourseMapper;
import com.university.core.dto.request.CreateCourseRequest;
import com.university.core.dto.request.UpdateCourseRequest;
import com.university.core.dto.response.CourseResponse;
import com.university.core.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(
                courseService.getAllCourses()
                        .stream()
                        .map(CourseMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable int id) {
        return ResponseEntity.ok(
                CourseMapper.toResponse(courseService.findCourseById(id))
        );
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<List<CourseResponse>> getCourseByInstructorId(@PathVariable int id) {
        List<CourseResponse> courses = courseService.findCoursesByInstructorId(id)
                .stream()
                .map(CourseMapper::toResponse)
                .toList()
                ;
        return ResponseEntity.ok(courses
        );
    }


    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CreateCourseRequest courseRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CourseMapper.toResponse(
                                courseService.createCourse(courseRequest)
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable int id,
            @Valid @RequestBody UpdateCourseRequest courseDetails) {

        return ResponseEntity.ok(
                CourseMapper.toResponse(
                        courseService.updateCourse(id, courseDetails)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
