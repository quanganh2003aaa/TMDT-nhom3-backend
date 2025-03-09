package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.IntrospectRequest;
import com.example.betmdtnhom3.dto.request.LogoutRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.AuthenticationServiceImpl;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationServiceImpl authenticationServiceImp;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        ApiResponse apiResponse = new ApiResponse();
        var result = authenticationServiceImp.authenticate(request);
        apiResponse.setResult(result);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/introspect")
    public ResponseEntity<?> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        ApiResponse apiResponse = new ApiResponse();
        var result = authenticationServiceImp.introspect(request);
        apiResponse.setResult(result);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        ApiResponse apiResponse = new ApiResponse();
        authenticationServiceImp.logout(request);
        apiResponse.setResult(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
