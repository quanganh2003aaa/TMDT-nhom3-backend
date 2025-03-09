package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.OrderServiceImpl;
import com.example.betmdtnhom3.service.impl.PaymentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/vnpay")
public class PaymentController {
    @Autowired
    PaymentServiceImpl paymentService;
    @Autowired
    OrderServiceImpl orderService;

    @PostMapping("/create/{id}")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @PathVariable  int id)
            throws UnsupportedEncodingException, JsonProcessingException {
        ApiResponse apiResponse = paymentService.createVNPay(request, id);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/vn-pay-callback")
    public RedirectView payCallbackHandler(HttpServletRequest request) {
        String redirectUrl = "http://127.0.0.1:5501/thankyou.html";
        try {
            String idOrder = request.getParameter("vnp_OrderInfo");
            String responseCode = request.getParameter("vnp_ResponseCode");
            if (responseCode.equals("00")){
                orderService.paySuccess(Integer.parseInt(idOrder));
            }
        } catch (Exception e) {
            System.err.println("Error during VNPay callback: " + e.getMessage());
        }
        return new RedirectView(redirectUrl);
    }
}
