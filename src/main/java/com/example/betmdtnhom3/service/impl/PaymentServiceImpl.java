package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.request.CreateOrderRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface PaymentServiceImpl {
    ApiResponse createVNPay(HttpServletRequest request, int idOrder) throws UnsupportedEncodingException, JsonProcessingException;

}
