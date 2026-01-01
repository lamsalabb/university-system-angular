package com.university.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id", "semester"}, name = "unique_enrollment")// to define the unique combination stays from SQL side
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)//many enrollments for one student
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)//many enrollment for one course, instructor detail inside course itself
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;

    @Column(name = "semester",nullable = false)
    private String semester;

    @Column(name="grade")
    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private Status status = Status.ENROLLED;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    public enum Status{
        ENROLLED, DROPPED, COMPLETED
    }
}
