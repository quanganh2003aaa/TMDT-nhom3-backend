package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.InfoStoreDTO;
import com.example.betmdtnhom3.entity.InfoStore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InfoStoreMapper {
    @Mapping(source = "user.name", target = "user")
    InfoStoreDTO toInfoStoreDTO (InfoStore infoStore);
}
