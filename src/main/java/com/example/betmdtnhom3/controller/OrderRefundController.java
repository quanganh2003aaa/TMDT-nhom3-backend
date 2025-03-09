package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.ChangeStatusRefundRequest;
import com.example.betmdtnhom3.dto.request.CreateOrderRefundRequest;
import com.example.betmdtnhom3.dto.request.CreateOrderRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.OrderRefundServiceImpl;
import com.example.betmdtnhom3.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refund")
public class OrderRefundController {
    @Autowired
    OrderRefundServiceImpl orderRefundService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllOrder(@RequestParam(defaultValue = "1", required = false) int page,
                                         @RequestParam(defaultValue = "", required = false) String query,
                                         @RequestParam(defaultValue = "0", required = false) int select){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderRefundService.getOrderRefund(page, query, select));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CreateOrderRefundRequest createOrderRefundRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderRefundService.create(createOrderRefundRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderRefundService.confirmOrderRefund(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/deliver")
    public ResponseEntity<?> deliver(@RequestBody @Valid ChangeStatusRefundRequest changeStatusRefundRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderRefundService.deliverOrder(changeStatusRefundRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/success/{id}")
    public ResponseEntity<?> success(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderRefundService.successOrderRefund(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderRefundService.rejectOrderRefund(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestBody @Valid ChangeStatusRefundRequest changeStatusRefundRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderRefundService.cancelOrderRefund(changeStatusRefundRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderRefundService.deleteOrderRefund(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
