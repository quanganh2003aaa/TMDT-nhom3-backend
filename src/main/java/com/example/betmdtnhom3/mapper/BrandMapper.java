package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.BrandDTO;
import com.example.betmdtnhom3.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDTO toBrandDTO(Brand brand);
}
