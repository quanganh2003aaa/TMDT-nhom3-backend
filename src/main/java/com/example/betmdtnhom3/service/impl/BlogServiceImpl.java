package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.BlogDTO;
import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateBlogRequest;

import java.util.List;

public interface BlogServiceImpl {
    Boolean createBlog(CreateBlogRequest createBlogRequest);
    Boolean updateBlog(int blogId, UpdateBlogRequest updateBlogRequest);
    Boolean deteleBlog(String id);
    List<BlogDTO> getAll(String query, int select);
}
