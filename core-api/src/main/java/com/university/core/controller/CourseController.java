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
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping
    public List<CourseResponse> getAllCourses(){
        return courseService.getAllCourses().stream().map(CourseMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable int id){
        CourseResponse course = CourseMapper.toResponse(courseService.findCourseById(id));
        return new ResponseEntity<>(course, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CreateCourseRequest courseRequest){
        CourseResponse createdCourse = CourseMapper.toResponse(courseService.createCourse(courseRequest));
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable int id,@Valid @RequestBody UpdateCourseRequest courseDetails){
        CourseResponse updatedCourse = CourseMapper.toResponse(courseService.updateCourse(id, courseDetails));
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id){
            courseService.deleteCourse(id);
            return new ResponseEntity<>(
                    Map.of("message","Course Deleted Successfully."),
                    HttpStatus.OK
            );
    }


}
