package com.university.attendance.repository;

import com.university.common.entity.Attendance;
import com.university.common.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEnrollmentId(int enrollmentId);

    List<Attendance> findByEnrollmentStudentId(int studentId);

    List<Attendance> findByEnrollmentStudentIdAndEnrollmentCourseId(int studentId, int courseId);

    Optional<Attendance> findByEnrollmentIdAndSessionDate(int enrollmentId, LocalDate sessionDate);
}
