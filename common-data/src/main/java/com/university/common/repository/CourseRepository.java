package com.university.common.repository;

import com.university.common.entity.Course;
import com.university.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<Course> findByCode(String code);
    List<Course> findByInstructor(User instructor);
}
