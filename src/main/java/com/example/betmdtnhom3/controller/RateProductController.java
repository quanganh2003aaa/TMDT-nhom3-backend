package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateRateProductRequest;
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

    @GetMapping("/getByProduct/{id}")
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
}
