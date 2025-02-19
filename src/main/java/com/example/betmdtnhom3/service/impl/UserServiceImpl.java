package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.UserDTO;
import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.SignUpRequest;
//Dinh nghia cac phuong thuc ma service can lam
public interface UserServiceImpl {
    Boolean createUser(SignUpRequest signUpRequest);
    UserDTO login(AuthenticationRequest authenticationRequest);
}
