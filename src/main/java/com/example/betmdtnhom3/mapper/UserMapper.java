package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.UserDTO;
import com.example.betmdtnhom3.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//Chuyen doi du lieu giua user va userdto
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);
}
