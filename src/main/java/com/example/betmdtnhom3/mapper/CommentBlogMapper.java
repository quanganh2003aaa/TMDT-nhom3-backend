package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.CommentBlogDTO;
import com.example.betmdtnhom3.dto.request.CreateCommentBlogRequest;
import com.example.betmdtnhom3.entity.CommentBlog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface CommentBlogMapper {
    @Mapping(source = "userId", target = "user.id")
    CommentBlog toCmtBlog(CreateCommentBlogRequest request);
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "blog.id", target = "blogId")
    CommentBlogDTO toCmtBlogDTO(CommentBlog commentBlog);
}
