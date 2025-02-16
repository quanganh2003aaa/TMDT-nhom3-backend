package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.VoucherDTO;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/voucher")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCate(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.getAllVouchers());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable String id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.getVoucherById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVoucher(@RequestBody VoucherDTO dto) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.createVoucher(dto));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable String id, @RequestBody VoucherDTO voucherDTO) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(voucherService.updateVoucher(id, voucherDTO));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable String id) {
        boolean isDeleted = voucherService.deleteVoucher(id);
        if (isDeleted) {
            return ResponseEntity.ok(Map.of("message", "Xóa voucher thành công"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Voucher không tồn tại"));
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyVoucher(@RequestParam String id, @RequestParam int totalPrice) {
        Map<String, Object> result = voucherService.applyVoucher(id, totalPrice);

        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}