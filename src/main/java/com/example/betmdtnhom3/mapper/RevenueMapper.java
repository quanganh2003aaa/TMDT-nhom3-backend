package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.RevenueDTO;
import com.example.betmdtnhom3.entity.Revenue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RevenueMapper {
    RevenueDTO toRevenue(Revenue revenue);
}
