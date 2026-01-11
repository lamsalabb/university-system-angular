package com.university.attendance.repository;

import com.university.attendance.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByEnrollmentStudentId(int studentId);

    Page<Attendance> findByEnrollmentCourseId(int courseId, Pageable pageable);

    List<Attendance> findByEnrollmentCourseId(int courseId);


    List<Attendance> findByEnrollmentStudentIdAndEnrollmentCourseId(int studentId, int courseId);

    boolean existsByEnrollmentIdAndSessionDate(int enrollmentId, LocalDate sessionDate);

}
