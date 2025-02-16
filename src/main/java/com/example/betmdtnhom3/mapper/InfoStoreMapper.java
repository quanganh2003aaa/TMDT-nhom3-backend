package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.InfoStoreDTO;
import com.example.betmdtnhom3.dto.ProductDTO;
import com.example.betmdtnhom3.entity.InfoStore;
import com.example.betmdtnhom3.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InfoStoreMapper {
    @Mapping(source = "user.id", target = "user")
    InfoStoreDTO toInfoStoreDTO(InfoStore infoStore);
}
