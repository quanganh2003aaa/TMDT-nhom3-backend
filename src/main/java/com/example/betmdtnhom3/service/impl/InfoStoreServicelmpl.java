package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.InfoStoreDTO;
import com.example.betmdtnhom3.dto.request.CreateInfoStoreRequest;
import com.example.betmdtnhom3.dto.request.UpdateInfoStoreRequest;

import java.util.List;

public interface InfoStoreServicelmpl {
    boolean create(CreateInfoStoreRequest createInfoStoreRequest);
    boolean update(int id, UpdateInfoStoreRequest updateInfoStoreRequest);
    boolean delete(int id);
    List<InfoStoreDTO> getAll();
    InfoStoreDTO getById(int id);
}
