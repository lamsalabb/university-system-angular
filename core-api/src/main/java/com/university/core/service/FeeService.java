package com.university.core.service;

import com.university.common.entity.Fee;
import com.university.common.entity.User;
import com.university.common.repository.UserRepository;
import com.university.fee.exception.FeeNotFoundException;
import com.university.fee.repository.FeeRepository;
import com.university.core.exception.UserNotFoundException;
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

    public List<Fee> getFeesByStudent(int studentId){
        return feeRepository.findByStudentId(studentId);
    }

    public Fee getFeeById(int id){
        return feeRepository.findById(id).orElseThrow(
                () -> new FeeNotFoundException("Fee not found with id: " + id)
        );
    }

    @Transactional
    public Fee createFee(Fee feeRequest){
        User student = userRepository.findById(feeRequest.getStudent().getId()).orElseThrow(
                () -> new UserNotFoundException("Student not found with id: " + feeRequest.getStudent().getId())
        );

        feeRequest.setStudent(student);

        feeRequest.setPaid(false);

        return feeRepository.save(feeRequest);

    }

    @Transactional
    public Fee markFeePaid(int feeId ){
        Fee fee = feeRepository.findById(feeId).orElseThrow(
                () -> new FeeNotFoundException("Fee not found with id: " + feeId)
        );

        fee.setPaid(true);
        fee.setPaymentDate(LocalDate.now());

        return feeRepository.save(fee);
    }

    @Transactional
    public Fee markFeeUnpaid(int feeId){
        Fee fee = feeRepository.findById(feeId).orElseThrow(
                () -> new FeeNotFoundException("Fee not found with id: " + feeId)
        );

        fee.setPaid(false);
        fee.setPaymentDate(null);

        return feeRepository.save(fee);
    }

    public int calculateOutstandingFee(int studentId){
        List<Fee> unpaidFees = feeRepository.findByStudentIdAndIsPaidFalse(studentId);

        int sum = 0;
        for (Fee fee: unpaidFees){
            sum+=fee.getAmount();
        }

        return sum;
    }

    public boolean hasOutstandingFeesAboveThreshold(int studentId, int thresholdAmount){
        int outstanding = calculateOutstandingFee(studentId);
        return outstanding > thresholdAmount;
    }

}
