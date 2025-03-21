package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.UserDTO;
import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.ForgotPasswordRequest;
import com.example.betmdtnhom3.dto.request.SignUpRequest;
import com.example.betmdtnhom3.dto.request.UpdateUserRequest;

import java.util.List;

public interface UserServiceImpl {
    Boolean createUser(SignUpRequest signUpRequest);
    UserDTO login(AuthenticationRequest authenticationRequest);
    boolean updateUser(String id, UpdateUserRequest request);
    boolean deleteUser(String id);
    List<UserDTO> getAllAdmin();
    List<UserDTO> getAllClient();
    Boolean createAdmin(SignUpRequest signUpRequest);
    boolean updateAdmin(String id, UpdateUserRequest request);
    UserDTO getById(String id);
    boolean forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    long count();
}
