package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateRateProductRequest;
import com.example.betmdtnhom3.dto.request.UpdateRateRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.RateProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rate")
public class RateProductController {
    @Autowired
    RateProductServiceImpl rateProductService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id, @RequestParam(defaultValue = "1", required = false) int page){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(rateProductService.getByProduct(id, page));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRateProduct(@RequestBody @Valid CreateRateProductRequest createRateProductRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(rateProductService.create(createRateProductRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRate(@PathVariable int id, @RequestBody UpdateRateRequest updateRateRequest){
        boolean isUpdated = rateProductService.update(id, updateRateRequest);
        if(isUpdated){
            return ResponseEntity.ok("Cập nhật đánh giá thành công");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật đánh giá thất bại");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRate(@PathVariable int id){
        boolean isDeleted = rateProductService.delete(id);
        if(isDeleted){
            return ResponseEntity.ok("Xoá đánh giá thành công");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Xóa đánh giá thất bại");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRates(@RequestParam(defaultValue = "1", required = false) int page){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(rateProductService.getAllRates(page));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
