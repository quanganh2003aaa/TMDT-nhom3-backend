package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateOrderRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllOrder(@RequestParam(defaultValue = "1", required = false) int page){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.getAllOrder(page));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.createOrder(createOrderRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
