package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.request.CreateRateProductRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;

public interface RateProductServiceImpl {
    boolean create(CreateRateProductRequest createRateProductRequest);
    PagenationDTO getByProduct(String idProduct, int page);
}
