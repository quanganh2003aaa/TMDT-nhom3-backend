package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.CategoryDTO;
import com.example.betmdtnhom3.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toCateDTO(Category category);
}
