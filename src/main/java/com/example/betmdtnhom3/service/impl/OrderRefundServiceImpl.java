package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.request.ChangeStatusRefundRequest;
import com.example.betmdtnhom3.dto.request.CreateOrderRefundRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;

public interface OrderRefundServiceImpl {
    boolean create(CreateOrderRefundRequest createOrderRefundRequest);
    Boolean confirmOrderRefund(int id);
    Boolean deliverOrder(ChangeStatusRefundRequest changeStatusRefundRequest);
    Boolean successOrderRefund(int id);
    Boolean rejectOrderRefund(int id);
    Boolean cancelOrderRefund(ChangeStatusRefundRequest changeStatusRefundRequest);
    Boolean deleteOrderRefund(int id);
    PagenationDTO getOrderRefund(int page, String query, int select);
}
