package com.university.core.service;

import com.university.common.entity.Fee;
import com.university.common.entity.User;
import com.university.common.repository.UserRepository;
import com.university.core.dto.request.CreateFeeRequest;
import com.university.core.exception.UserNotFoundException;
import com.university.fee.exception.FeeNotFoundException;
import com.university.fee.repository.FeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeeService {

    private final FeeRepository feeRepository;
    private final UserRepository userRepository;

    public FeeService(FeeRepository feeRepository, UserRepository userRepository) {
        this.feeRepository = feeRepository;
        this.userRepository = userRepository;
    }

    public List<Fee> getFeesByStudent(int studentId) {
        return feeRepository.findByStudentId(studentId);
    }

    public Fee getFeeById(int id) {
        return feeRepository.findById(id)
                .orElseThrow(() ->
                        new FeeNotFoundException("Fee not found with id: " + id)
                );
    }

    @Transactional
    public Fee createFee(CreateFeeRequest request) {

        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Student not found with id: " + request.getStudentId()
                        )
                );

        Fee fee = Fee.builder()
                .student(student)
                .amount(request.getAmount())
                .type(request.getType())
                .dueDate(request.getDueDate())
                .isPaid(false)
                .paymentDate(null)
                .build();

        return feeRepository.save(fee);
    }

    @Transactional
    public Fee markFeePaid(int feeId) {

        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() ->
                        new FeeNotFoundException("Fee not found with id: " + feeId)
                );

        if (!fee.isPaid()) {
            fee.setPaid(true);
            fee.setPaymentDate(LocalDate.now());
        }

        return fee;
    }

    @Transactional
    public Fee markFeeUnpaid(int feeId) {

        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() ->
                        new FeeNotFoundException("Fee not found with id: " + feeId));

        fee.setPaid(false);
        fee.setPaymentDate(null);

        return fee;
    }

    public int calculateOutstandingFee(int studentId) {
        return feeRepository.findByStudentIdAndIsPaidFalse(studentId)
                .stream()
                .mapToInt(Fee::getAmount)
                .sum();
    }

    public boolean hasOutstandingFeesAboveThreshold(int studentId, int thresholdAmount) {
        return calculateOutstandingFee(studentId) > thresholdAmount;
    }
}
