package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.BlogDTO;
import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    @Mapping(source = "authorId", target = "user.id")
    Blog toBlog(CreateBlogRequest request);

    @Mapping(source = "user.name", target = "authorName")
    BlogDTO toblogDTO(Blog blog);
}
