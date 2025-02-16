package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.SignUpRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid SignUpRequest signUpRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.createUser(signUpRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        ApiResponse apiResponse = new ApiResponse();
        var result = userService.login(request);
        apiResponse.setResult(result);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
