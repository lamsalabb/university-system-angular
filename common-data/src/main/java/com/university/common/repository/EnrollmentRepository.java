package com.university.common.repository;

import com.university.common.entity.Course;
import com.university.common.entity.Enrollment;
import com.university.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Integer> {

    List<Enrollment> findByStudentId(int studentId);

    List<Enrollment> findByCourseId(int courseId);

    Optional<Enrollment> findByStudentAndCourseAndSemester(User student, Course course, String semester);//using the unique constraint search
}
