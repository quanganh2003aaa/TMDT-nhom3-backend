package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.request.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.betmdtnhom3.entity.Category;
import com.example.betmdtnhom3.dto.CategoryDTO;
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "name", target = "name")
    CategoryDTO toCategoryDTO(Category category);

    @Mapping(source = "name", target = "name")
    Category toCategoryCreate(CreateCategoryRequest createCategoryRequest);

}
