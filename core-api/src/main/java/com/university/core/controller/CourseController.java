package com.university.core.controller;

import com.university.common.entity.Course;
import com.university.core.service.CourseService;
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
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable int id){
        Course course = courseService.findCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course){
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody Course courseDetails){
        Course updatedCourse = courseService.updateCourse(id, courseDetails);
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
