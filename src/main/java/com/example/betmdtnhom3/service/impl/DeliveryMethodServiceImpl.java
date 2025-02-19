package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.MethodDeliveryDTO;
import com.example.betmdtnhom3.dto.request.CreateMethodDeliveryRequest;
import com.example.betmdtnhom3.dto.request.UpdateMethodDeliveryRequest;

import java.util.List;

public interface DeliveryMethodServiceImpl {
    boolean create(CreateMethodDeliveryRequest createMethodDeliveryRequest);
    boolean update(int id, UpdateMethodDeliveryRequest updateMethodDeliveryRequest);
    boolean delete(int id);
    List<MethodDeliveryDTO> getAll();
    MethodDeliveryDTO getById(int id);

}