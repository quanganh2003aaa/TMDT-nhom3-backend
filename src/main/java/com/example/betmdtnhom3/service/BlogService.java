package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.dto.BlogDTO;
import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateBlogRequest;
import com.example.betmdtnhom3.entity.Blog;
import com.example.betmdtnhom3.entity.User;
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
       User user = userReponsitory.findById(request.getAuthorId())
               .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

       Blog blog = blogMapper.toBlog(request);
       blog.setUser(user);
       if (blog.getCreatedAt() == null) {
           blog.setCreatedAt(LocalDateTime.now());
       }

       blogReponsitory.save(blog);
       return true;
   }
    @Override
    public Boolean updateBlog(int blogId, UpdateBlogRequest request) {
        Blog blog = blogReponsitory.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Bài viết không tồn tại!"));

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blogReponsitory.save(blog);
        return true;
    }

    @Override
    public Boolean deleteBlog(int id) {
        Blog blog = blogReponsitory.findById(id)
                .orElseThrow(() -> new RuntimeException("Bài viết không tồn tại!"));

        blogReponsitory.delete(blog);
        return true;
    }

    @Override
    public List<BlogDTO> getAllBlogs(String query, int select) {
        List<Blog> blogs = blogReponsitory.findAll();
        return blogs.stream().map(blogMapper::toblogDTO).collect(Collectors.toList());
    }
}
