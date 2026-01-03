package com.university.core.controller;

import com.university.core.dto.mapper.FeeMapper;
import com.university.core.dto.request.CreateFeeRequest;
import com.university.core.dto.response.FeeResponse;
import com.university.core.dto.response.FeeSummaryResponse;
import com.university.core.service.FeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @GetMapping("/student/{studentId}")
    public List<FeeResponse> getFeesByStudent(@PathVariable int studentId) {
        return feeService.getFeesByStudent(studentId)
                .stream()
                .map(FeeMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeResponse> getFeeById(@PathVariable int id) {
        return ResponseEntity.ok(
                FeeMapper.toResponse(feeService.getFeeById(id))
        );
    }

    @PostMapping
    public ResponseEntity<FeeResponse> createFee(
            @Valid @RequestBody CreateFeeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FeeMapper.toResponse(feeService.createFee(request)));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<FeeResponse> payFee(@PathVariable int id) {
        return ResponseEntity.ok(
                FeeMapper.toResponse(feeService.markFeePaid(id))
        );
    }

    @PostMapping("/{id}/unpay")
    public ResponseEntity<FeeResponse> unpayFee(@PathVariable int id) {
        return ResponseEntity.ok(
                FeeMapper.toResponse(feeService.markFeeUnpaid(id))
        );
    }

    @GetMapping("/student/{studentId}/summary")
    public FeeSummaryResponse getFeeSummary(@PathVariable int studentId) {
        return new FeeSummaryResponse(
                studentId,
                feeService.calculateOutstandingFee(studentId)
        );
    }
}
