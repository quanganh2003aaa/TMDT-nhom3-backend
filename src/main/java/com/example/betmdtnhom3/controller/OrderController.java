package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateOrderRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.OrderServiceImpl;
import com.example.betmdtnhom3.service.impl.PaymentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;

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

    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.confirmOrder(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/deliver/{id}")
    public ResponseEntity<?> deliver(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.deliverOrder(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/success/{id}")
    public ResponseEntity<?> success(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.successOrder(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancel(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.cancelOrder(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.deleteOrder(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getByUser/{id}")
    public ResponseEntity<?> getOrderByUser(@PathVariable String id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.getOrderByUser(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getOrderSearch")
    public ResponseEntity<?> getOrderById(@RequestParam(defaultValue = "1", required = false) int page,
                                          @RequestParam(defaultValue = "", required = false) String query,
                                          @RequestParam(defaultValue = "0", required = false) int select){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.getOrderSearch(page, query, select));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable int id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(orderService.getById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        long count = orderService.count();
        return ResponseEntity.ok(count);
    }
}
