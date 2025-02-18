package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.BlogDTO;
import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateBlogRequest;
import com.example.betmdtnhom3.entity.Blog;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.BlogMapper;
import com.example.betmdtnhom3.responsitory.BlogReponsitory;
import com.example.betmdtnhom3.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BlogService implements BlogServiceImpl {

    BlogReponsitory blogReponsitory;

    BlogMapper blogMapper;

    @Override
    public Boolean createBlog(CreateBlogRequest createBlogRequest) {
        boolean isSuccess = false;
        Blog blog = blogMapper.toBlogCreate(createBlogRequest);

        try {
            blogReponsitory.save(blog);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.BLOG_CREATE_ERROR);
        }

        return isSuccess;
    }

    @Override
    public Boolean updateBlog(int blogId, UpdateBlogRequest updateBlogRequest) {
        boolean isSuccess = false;

        try {
            Blog blog = blogReponsitory.findById(String.valueOf(blogId))
                    .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
            blog.setTitle(updateBlogRequest.getTitle());
            blog.setContent(updateBlogRequest.getContent());
            blogReponsitory.save(blog);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.BLOG_UPDATE_ERROR);
        }

        return isSuccess;
    }

    @Override
    public Boolean deteleBlog(String id) {
        boolean isSuccess = false;

        try {
            Blog blog = blogReponsitory.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
            blogReponsitory.deleteById(id);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.BLOG_DELETE_ERROR);
        }

        return isSuccess;
    }

    @Override
    public List<BlogDTO> getAll(String query, int select) {
        List<BlogDTO> blogDTOList = new ArrayList<>();
            List<Blog> blogList = blogReponsitory.findAll();
            for (Blog blog : blogList) {
                BlogDTO blogDTO = blogMapper.toBlogDTO(blog);
                blogDTOList.add(blogDTO);
            }

        return blogDTOList;
    }
}
