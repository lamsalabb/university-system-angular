package com.university.core.service;

import com.university.common.dto.request.CreateEnrollmentRequest;
import com.university.common.entity.Course;
import com.university.common.entity.Enrollment;
import com.university.common.entity.User;
import com.university.common.exception.*;
import com.university.common.repository.CourseRepository;
import com.university.common.repository.EnrollmentRepository;
import com.university.common.repository.UserRepository;
import com.university.core.security.annotation.SelfOnly;
import com.university.fee.entity.Fee;
import com.university.fee.exception.OutstandingFeesException;
import com.university.fee.repository.FeeRepository;
import com.university.fee.service.FeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final FeeRepository feeRepository;
    private final FeeService feeService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository, FeeRepository feeRepository, FeeService feeService) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.feeRepository = feeRepository;
        this.feeService = feeService;
    }

    //save a new enrollment
    @Transactional
    public Enrollment enroll(CreateEnrollmentRequest request) {

        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Student not found with id: " + request.getStudentId()
                        )
                );

        if (student.getRole() != User.Role.STUDENT) {
            throw new NonStudentEnrollmentException("Only students can be enrolled");
        }

        int threshold = 4000;
        if (feeService.hasOutstandingFeesAboveThreshold(student.getId(), threshold)) {
            throw new OutstandingFeesException(
                    "Enrollment blocked: Student must clear outstanding fees. Total Outstanding: " + threshold
            );
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new CourseNotFoundException(
                                "Course not found with id: " + request.getCourseId()
                        )
                );

        enrollmentRepository
                .findByStudentAndCourseAndSemester(student, course, request.getSemester())
                .ifPresent(e -> {
                    throw new EnrollmentAlreadyExistsException(
                            "Student already enrolled for this course and semester"
                    );
                });

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .semester(request.getSemester())
                .status(Enrollment.Status.ENROLLED)
                .enrollmentDate(LocalDate.now())
                .build();

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        Fee fee = Fee.builder()
                .student(student)
                .amount(course.getCost())
                .type(Fee.Type.TUITION)
                .isPaid(false)
                .dueDate(LocalDate.now().plusMonths(3))
                .build();

        feeRepository.save(fee);

        return savedEnrollment;

    }


    @Transactional//drop enrollment for students
    public void dropEnrollment(int enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() ->
                        new EnrollmentNotFoundException(
                                "Enrollment not found with id: " + enrollmentId
                        )
                );

        enrollment.setStatus(Enrollment.Status.DROPPED);
    }


    @SelfOnly
    public List<Enrollment> getEnrollmentByStudent(int id) {
        return enrollmentRepository.findByStudentId(id);
    }

    public List<Enrollment> getEnrollmentByCourse(int courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }


    public Page<Enrollment> getAllEnrollments(Pageable pageable) {
        return enrollmentRepository.findAll(pageable);
    }

    public Page<Enrollment> getEnrollmentsByInstructor(int instructorId, Pageable pageable) {
        return enrollmentRepository.findByCourseInstructorId(instructorId, pageable);
    }

    public Enrollment getEnrollmentById(int id) {
        return enrollmentRepository.findById(id).orElseThrow(
                () -> new EnrollmentNotFoundException("Enrollment not found with id: " + id)
        );
    }

    @Transactional
    public void updateGrade(Integer enrollmentId, String grade) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));

        if (enrollment.getStatus().toString().equals("DROPPED")) {
            throw new IllegalStateException("Cannot update grade for dropped enrollment");
        }

        enrollment.setGrade(grade);
    }


}
