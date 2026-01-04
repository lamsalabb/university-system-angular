package com.university.core.controller;

import com.university.core.dto.mapper.FeeMapper;
import com.university.core.dto.request.CreateFeeRequest;
import com.university.core.dto.response.FeeResponse;
import com.university.core.dto.response.FeeSummaryResponse;
import com.university.core.service.FeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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


//    @GetMapping
//    public ResponseEntity<List<FeeResponse>> getAllFees(){
//        List<FeeResponse> fees = feeService.getAllFees()
//                .stream()
//                .map(FeeMapper::toResponse)
//                .toList();
//
//        return ResponseEntity.ok(fees);
//    }

    @GetMapping
    public ResponseEntity<Page<FeeResponse>> getAllFees(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ){
        Page<FeeResponse> fees = feeService.getAllFees(pageable)
                .map(FeeMapper::toResponse);


        return ResponseEntity.ok(fees);
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

    @PutMapping("/{id}/toggle")
    public ResponseEntity<FeeResponse> toggleFee(@PathVariable int id) {
        return ResponseEntity.ok(
                FeeMapper.toResponse(feeService.toggleFee(id))
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
