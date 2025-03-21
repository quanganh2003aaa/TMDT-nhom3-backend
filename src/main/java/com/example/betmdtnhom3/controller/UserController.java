package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.UserDTO;
import com.example.betmdtnhom3.dto.request.*;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.GmailServiceImpl;
import com.example.betmdtnhom3.service.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    GmailServiceImpl gmailService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid SignUpRequest signUpRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.createUser(signUpRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.getById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody @Valid SignUpRequest signUpRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.createAdmin(signUpRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        ApiResponse apiResponse = new ApiResponse();
        var result = userService.login(request);
        apiResponse.setResult(result);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.updateUser(id, request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/updateAdmin/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable String id, @RequestBody UpdateUserRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.updateAdmin(id, request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return userService.deleteUser(id) ? ResponseEntity.ok("Xoá thành công") : ResponseEntity.badRequest().body("Xóa thất bại");
    }

    @GetMapping("/list/admin")
    public ResponseEntity<List<UserDTO>> getAllAdmin(){
        return ResponseEntity.ok(userService.getAllAdmin());
    }

    @GetMapping("/list/client")
    public ResponseEntity<List<UserDTO>> getAllClient(){
        return ResponseEntity.ok(userService.getAllClient());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        long count = userService.count();
        return ResponseEntity.ok(count);
    }

    @PostMapping("/otp")
    public boolean otp(@RequestBody @Valid OTPRequest otpRequest) throws MessagingException, IOException {
        return gmailService.sendOTPGmail(otpRequest.getGmail());
    }

    @PostMapping("/newPassword")
    public boolean otp(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        return userService.forgotPassword(forgotPasswordRequest);
    }
}
