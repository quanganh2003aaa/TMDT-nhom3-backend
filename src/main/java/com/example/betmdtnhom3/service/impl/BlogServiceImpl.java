package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.BlogDTO;
import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateBlogRequest;

import java.util.List;

public interface BlogServiceImpl {
    Boolean createBlog(CreateBlogRequest request);
    Boolean updateBlog(int blogId, UpdateBlogRequest request);
    Boolean deleteBlog(int id);
    List<BlogDTO> getAllBlogs();
    BlogDTO getById(int id);
}
