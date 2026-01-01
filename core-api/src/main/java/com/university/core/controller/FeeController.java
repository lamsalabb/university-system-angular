package com.university.core.controller;


import com.university.common.entity.Fee;
import com.university.core.service.FeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }


    @GetMapping("/student/{studentId}")
    public List<Fee> getFeesByStudent(@PathVariable int studentId){
        return feeService.getFeesByStudent(studentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFeeById(@PathVariable int id){
        Fee fee = feeService.getFeeById(id);
        return new ResponseEntity<>(fee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createFee(@RequestBody Fee feeRequest){
        Fee created = feeService.createFee(feeRequest);
        return new ResponseEntity<>(created,HttpStatus.CREATED);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payFee(@PathVariable int id){
        Fee updated = feeService.markFeePaid(id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping("/{id}/unpay")
    public ResponseEntity<?> unpayFee(@PathVariable int id){
        Fee updated = feeService.markFeeUnpaid(id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/summary")
    public ResponseEntity<?> getFeeSummary(@PathVariable int studentId){
        int outstanding = feeService.calculateOutstandingFee(studentId);
        return new ResponseEntity<>(
                Map.of("StudentId", studentId,
                        "outstandingAmount",outstanding
                        ),
                HttpStatus.OK
        );
    }


}
