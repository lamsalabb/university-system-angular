package com.university.core.service;

import com.university.common.entity.Course;
import com.university.common.entity.Enrollment;
import com.university.common.entity.User;
import com.university.common.repository.CourseRepository;
import com.university.common.repository.EnrollmentRepository;
import com.university.common.repository.UserRepository;
import com.university.core.exception.*;
import com.university.fee.exception.OutstandingFeesException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final FeeService feeService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository, FeeService feeService) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.feeService = feeService;
    }

    //save a new enrollment
    @Transactional
    public Enrollment enroll(Enrollment enrollmentRequest){

        User student = userRepository.findById(enrollmentRequest.getStudent().getId()).orElseThrow(
                () -> new UserNotFoundException("Student not found with id: " + enrollmentRequest.getStudent().getId())
        );

        if(!"STUDENT".equalsIgnoreCase(student.getRole().name())){
            throw new NonStudentEnrollmentException("Only students can be enrolled");
        }

        int threshold = 4000;
        if(feeService.hasOutstandingFeesAboveThreshold(student.getId(), threshold)){
            throw new OutstandingFeesException("Enrollment blocked: Please clear the outstanding fees. Exceeded: "+ threshold);
        }
        Course course = courseRepository.findById(enrollmentRequest.getCourse().getId()).orElseThrow(
                () -> new CourseNotFoundException("Course not found with id: " + enrollmentRequest.getCourse().getId())
        );

        enrollmentRepository.findByStudentAndCourseAndSemester(enrollmentRequest.getStudent(),enrollmentRequest.getCourse(),enrollmentRequest.getSemester()).ifPresent(
                (enrollNotUnique) -> {
                    throw new EnrollmentAlreadyExistsException("Student is already enrolled in this course for the given semester.");
                }
        );

        enrollmentRequest.setStatus(Enrollment.Status.ENROLLED);
        enrollmentRequest.setEnrollmentDate(LocalDate.now());

        return enrollmentRepository.save(enrollmentRequest);

    }

    @Transactional//drop enrollment for students
    public void dropEnrollment(int enrollmentId){
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(
                () -> new EnrollmentNotFoundException("Enrollment not found with id: " + enrollmentId)
        );

        enrollment.setStatus(Enrollment.Status.DROPPED);
        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollmentByStudent(int studentId){
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentByCourse(int courseId){
        return enrollmentRepository.findByCourseId(courseId);
    }

    public Enrollment getEnrollmentById(int id){
        return enrollmentRepository.findById(id).orElseThrow(
                () -> new EnrollmentNotFoundException("Enrollment not found with id: " + id)
        );
    }



}
