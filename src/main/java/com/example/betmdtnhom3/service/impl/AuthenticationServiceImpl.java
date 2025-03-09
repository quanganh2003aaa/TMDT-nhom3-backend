package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.AuthenticationDTO;
import com.example.betmdtnhom3.dto.IntrospectDTO;
import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.IntrospectRequest;
import com.example.betmdtnhom3.dto.request.LogoutRequest;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationServiceImpl {
    AuthenticationDTO authenticate(AuthenticationRequest authenticationRequest);
    IntrospectDTO introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    void logout(LogoutRequest request) throws JOSEException, ParseException;
}
