package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.UserDTO;
import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.SignUpRequest;
import com.example.betmdtnhom3.dto.request.UpdateUserRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Xu ly API request tu user, goi userserviceIpml thuc hien dky dang nhap
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

    @PostMapping("/create-admin")
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
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request){
        UserDTO updateUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updateUser);
    }

    @PutMapping("/update-admin/{id}")
    public ResponseEntity<UserDTO> updateAdmin(@PathVariable String id, @RequestBody UpdateUserRequest request){
        UserDTO updatedAdmin = userService.updateAdmin(id, request);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return userService.deleteUser(id) ? ResponseEntity.ok("Xoá thành công") : ResponseEntity.badRequest().body("Xóa thất bại");
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
