package com.university.core.service;

import com.university.common.entity.Course;
import com.university.common.entity.User;
import com.university.common.repository.CourseRepository;
import com.university.common.repository.UserRepository;
import com.university.common.dto.request.CreateCourseRequest;
import com.university.common.dto.request.UpdateCourseRequest;
import com.university.common.exception.CourseAlreadyExistsException;
import com.university.common.exception.CourseNotFoundException;
import com.university.common.exception.InvalidInstructorException;
import com.university.common.exception.UserNotFoundException;
import com.university.common.annotation.SelfOnly;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }


    @Transactional//CREATE
    public Course createCourse(CreateCourseRequest courseRequest) {
        if (courseRepository.findByCode(courseRequest.getCode()).isPresent()) {
            throw new CourseAlreadyExistsException("Course with code " + courseRequest.getCode() + " already exists.");
        }
        User instructor = userRepository.findById(courseRequest.getInstructorId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Instructor not found with id: " + courseRequest.getInstructorId()
                        )
                );

        if (instructor.getRole() != User.Role.INSTRUCTOR) {
            throw new InvalidInstructorException("User is not an instructor");
        }

        Course course = Course.builder()
                .title(courseRequest.getTitle())
                .code(courseRequest.getCode()).credits(courseRequest.getCredits()).instructor(instructor).description(courseRequest.getDescription()).build();
        return courseRepository.save(course);
    }


    //READ
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(int id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
    }


    @Transactional // UPDATE
    public Course updateCourse(int id, UpdateCourseRequest request) {

        Course existingCourse = findCourseById(id);

        if (!existingCourse.getCode().equals(request.getCode())
                && courseRepository.findByCode(request.getCode()).isPresent()) {
            throw new CourseAlreadyExistsException(
                    "Course with code " + request.getCode() + " already exists."
            );
        }

        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Instructor not found with id: " + request.getInstructorId()
                        )
                );

        if (instructor.getRole() != User.Role.INSTRUCTOR) {
            throw new InvalidInstructorException("User is not an instructor");
        }

        existingCourse.setTitle(request.getTitle());
        existingCourse.setCode(request.getCode());
        existingCourse.setCredits(request.getCredits());
        existingCourse.setDescription(request.getDescription());
        existingCourse.setInstructor(instructor);

        return existingCourse;
    }


    @Transactional//DELETE
    public void deleteCourse(int id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("Cannot delete. Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @SelfOnly
    public List<Course> findCoursesByInstructorId(int id) {
        return courseRepository.findByInstructorId(id);
    }


}
