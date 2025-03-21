package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.OrderDTO;
import com.example.betmdtnhom3.dto.OrderGmailDTO;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;

public interface GmailServiceImpl {
    boolean sendOrderGmail(String toEmail, OrderGmailDTO orderGmailDTO);
    boolean sendOTPGmail(String toEmail) throws MessagingException, IOException;
}
