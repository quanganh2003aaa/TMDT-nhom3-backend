package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.OrderDTO;
import com.example.betmdtnhom3.dto.OrderListDTO;
import com.example.betmdtnhom3.dto.request.CreateOrderRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;

import java.util.List;

public interface OrderServiceImpl {
    PagenationDTO getAllOrder(int page);
    PagenationDTO getOrderSearch(int page, String query, int select);
    List<OrderListDTO> getOrderByUser(String idUser);
    OrderDTO getById(int id);
    Boolean createOrder(CreateOrderRequest createOrderRequest);
    Boolean confirmOrder(int id);
    Boolean deliverOrder(int id);
    Boolean cancelOrder(int id);
    Boolean successOrder(int id);
    Boolean deleteOrder(int id);

}
