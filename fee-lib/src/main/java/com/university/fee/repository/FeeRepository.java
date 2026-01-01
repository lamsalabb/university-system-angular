package com.university.fee.repository;

import com.university.common.entity.Fee;
import com.university.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Integer> {
    List<Fee> findByStudentId(int studentId);

    List<Fee> findByStudentIdAndIsPaidFalse(int studentId);
}
