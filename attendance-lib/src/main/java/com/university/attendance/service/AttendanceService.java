package com.university.attendance.service;

import com.university.attendance.exception.AttendanceNotFoundException;
import com.university.attendance.repository.AttendanceRepository;
import com.university.attendance.entity.Attendance;
import com.university.common.entity.Enrollment;
import com.university.common.repository.EnrollmentRepository;
import com.university.attendance.dto.request.MarkAttendanceRequest;
import com.university.attendance.exception.AttendanceAlreadyMarkedException;
import com.university.common.exception.EnrollmentNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Attendance markAttendance(
            int enrollmentId,
            LocalDate sessionDate,
            Attendance.Status status,
            String remarks
    ) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() ->
                        new EnrollmentNotFoundException(
                                "Enrollment not found with id: " + enrollmentId
                        )
                );

        if (attendanceRepository
                .existsByEnrollmentIdAndSessionDate(enrollmentId, sessionDate)) {
            throw new AttendanceAlreadyMarkedException("Attendance already marked for this session");
        }

        Attendance attendance = new Attendance();
        attendance.setEnrollment(enrollment);
        attendance.setSessionDate(sessionDate);
        attendance.setStatus(status);
        attendance.setRemarks(remarks);

        return attendanceRepository.save(attendance);
    }

    @Transactional
    public List<Attendance> markAttendanceBatch(
            List<MarkAttendanceRequest> requests
    ) {
        return requests.stream()
                .map(r ->
                        markAttendance(
                                r.getEnrollmentId(),
                                r.getSessionDate(),
                                r.getStatus(),
                                r.getRemarks()
                        )
                )
                .toList();
    }


    //student can view all their attendance records
    public List<Attendance> getAttendanceByStudent(int studentId) {
        return attendanceRepository.findByEnrollmentStudentId(studentId);
    }

    //student can view their attendance records for specific course
    public List<Attendance> getAttendanceByStudentAndCourse(int studentId, int courseId) {
        return attendanceRepository.findByEnrollmentStudentIdAndEnrollmentCourseId(studentId, courseId);
    }

    public Page<Attendance> getAttendanceByCourseId(int courseId, Pageable pageable) {

        return attendanceRepository.findByEnrollmentCourseId(courseId, pageable);
    }


    public AttendanceSummary getSummaryForStudentInCourse(int studentId, int courseId) {
        List<Attendance> records = attendanceRepository.findByEnrollmentStudentIdAndEnrollmentCourseId(studentId, courseId);

        return getAttendanceSummary(records);
    }

    public AttendanceSummary getSummaryForCourse(int courseId) {
        List<Attendance> records = attendanceRepository.findByEnrollmentCourseId(courseId);

        return getAttendanceSummary(records);
    }

    private AttendanceService.AttendanceSummary getAttendanceSummary(List<Attendance> records) {
        int totalSessions = records.size();
        long presentCount = records.stream().filter(a -> a.getStatus() == Attendance.Status.PRESENT).count();//long for stream function count()
        long absentCount = records.stream().filter(a -> a.getStatus() == Attendance.Status.ABSENT).count();
        long excusedCount = records.stream().filter(a -> a.getStatus() == Attendance.Status.EXCUSED).count();

        double presentPercent = totalSessions == 0 ? 0.0 : (presentCount * 100.0 / totalSessions);

        return new AttendanceSummary(totalSessions, presentCount, absentCount, excusedCount, presentPercent);
    }

    @Transactional
    public void updateStatus(int attendanceId, String status) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found"));

        attendance.setStatus(Attendance.Status.valueOf(status));
        attendanceRepository.save(attendance);
    }

    public record AttendanceSummary(int totalSessions, long presentCount, long absentCount, long excusedCount,
                                    double presentPercent) {
    }


}
