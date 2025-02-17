package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.InfoStoreDTO;
import com.example.betmdtnhom3.entity.InfoStore;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InfoStoreMapper {
    InfoStoreDTO toInfoStoreDTO (InfoStore infoStore);
}
