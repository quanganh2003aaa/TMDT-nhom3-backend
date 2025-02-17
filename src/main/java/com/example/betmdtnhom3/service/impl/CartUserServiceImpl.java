package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.CartDTO;
import com.example.betmdtnhom3.dto.request.AddToCartRequest;
import com.example.betmdtnhom3.dto.request.UpdateCartRequest;

public interface CartUserServiceImpl {
    boolean addToCart(AddToCartRequest addToCartRequest);
    boolean updateCart(String idUser, UpdateCartRequest updateCartRequest);
    boolean deleteCart(String idUser, int idCart);
    CartDTO getByUser(String idUser);
}
