package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.request.CreateRateProductRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;
import com.example.betmdtnhom3.dto.request.UpdateRateRequest;

public interface RateProductServiceImpl {
    boolean create(CreateRateProductRequest createRateProductRequest);
    PagenationDTO getByProduct(String idProduct, int page);
    PagenationDTO getByUser(String idUser, int page);
    boolean update(int id, UpdateRateRequest updateRateRequest);
    boolean delete(int id);
    PagenationDTO getAllRates(int page);
}
