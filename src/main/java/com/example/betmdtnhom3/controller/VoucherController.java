package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.ApplyVoucherRequest;
import com.example.betmdtnhom3.dto.request.CreateVoucherRequest;
import com.example.betmdtnhom3.dto.request.UpdateVoucherRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCate(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.getAllVouchers());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable String id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.getVoucherById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVoucher(@RequestBody @Valid CreateVoucherRequest createVoucherRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.createVoucher(createVoucherRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable String id, @RequestBody @Valid UpdateVoucherRequest updateVoucherRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.updateVoucher(id, updateVoucherRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable String id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.deleteVoucher(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> createVoucher(@RequestBody ApplyVoucherRequest applyVoucherRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.applyVoucher(applyVoucherRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}