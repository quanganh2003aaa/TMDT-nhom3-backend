package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.responsitory.ProductReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductReponsitory productReponsitory;

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productReponsitory.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
}
