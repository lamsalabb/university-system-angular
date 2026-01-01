package com.university.core.service;

import com.university.attendance.repository.AttendanceRepository;
import com.university.common.entity.Attendance;
import com.university.common.entity.Enrollment;
import com.university.common.repository.EnrollmentRepository;
import com.university.core.exception.EnrollmentNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.university.attendance.exception.AttendanceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;


    public AttendanceService(AttendanceRepository attendanceRepository, EnrollmentRepository enrollmentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    public Attendance markAttendance(int enrollmentId, LocalDate sessionDate, Attendance.Status status, String remarks){

        Enrollment enrollment = enrollmentRepository .findById(enrollmentId).orElseThrow(
                () -> new EnrollmentNotFoundException("Enrollment not found with id: " + enrollmentId)
        );

        return attendanceRepository.findByEnrollmentIdAndSessionDate(enrollmentId,sessionDate).map(
                existing -> {
                    existing.setStatus(status);
                    existing.setRemarks(remarks);//update a previously inserted attendance
                    return attendanceRepository.save(existing);
                }
        ).orElseGet(
                () -> {
                    Attendance attendance = new Attendance();
                    attendance.setEnrollment(enrollment);
                    attendance.setSessionDate(LocalDate.now());
                    attendance.setStatus(status);
                    attendance.setRemarks(remarks);
                    return attendanceRepository.save(attendance);
                }
        );
    }

    //student can view all their attendance records
    public List<Attendance> getAttendanceByStudent(int studentId){
        return attendanceRepository.findByEnrollmentStudentId(studentId);
    }

    //student can view their attendance records for specific course
    public List<Attendance> getAttendanceByStudentAndCourse(int studentId, int courseId){
        return attendanceRepository.findByEnrollmentStudentIdAndEnrollmentCourseId(studentId,courseId);
    }

    public Attendance getAttendanceById(int id){
        return attendanceRepository.findById(id).orElseThrow(
                () -> new AttendanceNotFoundException("Attendance not found with id: " + id)
        );
    }

    public AttendanceSummary getSummaryForStudentInCourse(int studentId, int courseId){
        List<Attendance> records = attendanceRepository.findByEnrollmentStudentIdAndEnrollmentCourseId(studentId, courseId);

        int totalSessions = records.size();
        long presentCount = records.stream().filter(a -> a.getStatus() == Attendance.Status.PRESENT).count();//long for stream function count()
        long absentCount = records.stream().filter(a -> a.getStatus() == Attendance.Status.ABSENT).count();
        long excusedCount = records.stream().filter(a -> a.getStatus() == Attendance.Status.EXCUSED).count();

        double presentPercent = totalSessions == 0? 0.0 :(presentCount*100.0/totalSessions);

        return new AttendanceSummary(totalSessions,presentCount,absentCount,excusedCount,presentPercent);
    }


    public record AttendanceSummary(int totalSessions, long presentCount, long absentCount, long excusedCount, double presentPercent) { }



}
