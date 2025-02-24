package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.BlogDTO;
import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateBlogRequest;
import com.example.betmdtnhom3.entity.Blog;
import com.example.betmdtnhom3.entity.User;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.BlogMapper;
import com.example.betmdtnhom3.responsitory.BlogReponsitory;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService implements BlogServiceImpl {
   @Autowired
    BlogReponsitory blogReponsitory;
   @Autowired
    BlogMapper blogMapper;
   @Autowired
    UserReponsitory userReponsitory;
   @Override
   public Boolean createBlog(CreateBlogRequest request) {
       boolean isSuccess = false;
       User user = userReponsitory.findById(request.getAuthorId())
               .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

       Blog blog = blogMapper.toBlog(request);
       blog.setUser(user);
       blog.setCreatedAt(LocalDateTime.now());
        try {
            blogReponsitory.save(blog);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.CREATE_BLOG_ERROR);
        }

       return isSuccess;
   }
    @Override
    public Boolean updateBlog(int blogId, UpdateBlogRequest request) {
        boolean isSuccess = false;
        Blog blog = blogReponsitory.findById(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        try {
            blogReponsitory.save(blog);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.UPDATE_BLOG_ERROR);
        }
        return isSuccess;
    }

    @Override
    public Boolean deleteBlog(int id) {
        boolean isSuccess = false;
        Blog blog = blogReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
        try {
            blogReponsitory.delete(blog);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.UPDATE_BLOG_ERROR);
        }
        return isSuccess;
    }

    @Override
    public List<BlogDTO> getAllBlogs() {
        List<Blog> blogs = blogReponsitory.findAll();
        return blogs.stream().map(blogMapper::toblogDTO).collect(Collectors.toList());
    }
}
