package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateMethodDeliveryRequest;
import com.example.betmdtnhom3.dto.request.UpdateMethodDeliveryRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.DeliveryMethodServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryMethodController {
    @Autowired
    DeliveryMethodServiceImpl deliveryMethodService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(deliveryMethodService.getAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(deliveryMethodService.getById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CreateMethodDeliveryRequest createMethodDeliveryRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(deliveryMethodService.create(createMethodDeliveryRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(deliveryMethodService.delete(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id,
                                    @RequestBody @Valid UpdateMethodDeliveryRequest updateMethodDeliveryRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(deliveryMethodService.update(id, updateMethodDeliveryRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
