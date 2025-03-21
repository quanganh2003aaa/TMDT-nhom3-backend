package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.RevenueDTO;

import java.util.List;

public interface RevenueServiceImpl {
    List<RevenueDTO> getChart();
    RevenueDTO getByMonthAndYear(int month, int year);
}
