package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.UserDTO;
import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.SignUpRequest;
import com.example.betmdtnhom3.dto.request.UpdateUserRequest;

import java.util.List;

//Dinh nghia cac phuong thuc ma service can lam
public interface UserServiceImpl {
    Boolean createUser(SignUpRequest signUpRequest);
    UserDTO login(AuthenticationRequest authenticationRequest);
    UserDTO updateUser(String id, UpdateUserRequest request);
    boolean deleteUser(String id);
    List<UserDTO> getAllUsers();
    Boolean createAdmin(SignUpRequest signUpRequest);
}
