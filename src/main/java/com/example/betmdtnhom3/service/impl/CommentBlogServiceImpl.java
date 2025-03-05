package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.CommentBlogDTO;
import com.example.betmdtnhom3.dto.request.CreateCommentBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateCommentBlogRequest;
import com.example.betmdtnhom3.entity.CommentBlog;

import java.util.List;

public interface CommentBlogServiceImpl {
    Boolean createCommentBlog(CreateCommentBlogRequest request);
    Boolean updateCommentBlog(int CommentBlogId, UpdateCommentBlogRequest request);
    Boolean deteleCommentBlog(int id);
    List<CommentBlogDTO> getAllCommentBlog(String query, int select);
}
