package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.BlogDTO;
import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    BlogDTO toBlogDTO(Blog blog);
    @Mapping(target = "id", ignore = true)
    Blog toBlogCreate(CreateBlogRequest createBlogRequest);
}
