package com.university.reporting.repository;

import com.university.common.entity.Enrollment;
import com.university.reporting.contract.dto.ActiveStudentDTO;
import com.university.reporting.contract.dto.AverageGradeDTO;
import com.university.reporting.contract.dto.CourseEnrollmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportingRepository extends JpaRepository<Enrollment, Integer> {

    //Active students
    @Query("""
                SELECT new com.university.reporting.contract.dto.ActiveStudentDTO(
                    u.id, u.email, u.firstName, u.lastName
                )
                FROM User u
                WHERE u.isActive = true AND u.role = com.university.common.entity.User.Role.STUDENT
            """)
    List<ActiveStudentDTO> findActiveStudents();

    //Courses with enrollment count
    @Query("""
                SELECT new com.university.reporting.contract.dto.CourseEnrollmentDTO(
                    c.id, c.code, COUNT(e.id)
                )
                FROM Enrollment e JOIN e.course c
                GROUP BY c.id, c.code
            """)
    List<CourseEnrollmentDTO> enrollmentByCourse();

    //Average grade per course
    @Query("""
             SELECT new com.university.reporting.contract.dto.AverageGradeDTO(
                 c.id,
                 c.code,
                 AVG(
                     CASE
                         WHEN e.grade = 'A+' THEN 4.0
                         WHEN e.grade = 'A' THEN 3.7
                         WHEN e.grade = 'B+' THEN 3.3
                         WHEN e.grade = 'B'  THEN 3.0
                         WHEN e.grade = 'B-' THEN 2.7
                         WHEN e.grade = 'C+' THEN 2.3
                         WHEN e.grade = 'C'  THEN 2.0
                         WHEN e.grade = 'C-' THEN 1.7
                         WHEN e.grade = 'D'  THEN 1.0
                         WHEN e.grade = 'F'  THEN 0.0
                         ELSE NULL
                     END
                 )
             )
             FROM Enrollment e
             JOIN e.course c
             WHERE e.grade IS NOT NULL
            GROUP BY c.id, c.code
            """)
    List<AverageGradeDTO> averageGrades();


}
