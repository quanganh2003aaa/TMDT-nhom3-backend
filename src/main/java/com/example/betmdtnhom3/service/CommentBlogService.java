package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.CommentBlogDTO;
import com.example.betmdtnhom3.dto.request.CreateCommentBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateCommentBlogRequest;
import com.example.betmdtnhom3.entity.Blog;
import com.example.betmdtnhom3.entity.CommentBlog;
import com.example.betmdtnhom3.entity.InfoUser;
import com.example.betmdtnhom3.entity.User;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.CommentBlogMapper;
import com.example.betmdtnhom3.responsitory.BlogReponsitory;
import com.example.betmdtnhom3.responsitory.CommentBlogRepository;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.CommentBlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class CommentBlogService implements CommentBlogServiceImpl {
    @Autowired
    CommentBlogRepository commentBlogRepository;
    @Autowired
    CommentBlogMapper commentBlogMapper;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    BlogReponsitory blogReponsitory;

    @Override
    public Boolean createCommentBlog(CreateCommentBlogRequest request) {
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            throw new RuntimeException("UserId không được để trống!");
        }
        User user = userReponsitory.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));
        Blog blog = blogReponsitory.findById(request.getBlogId())
                .orElseThrow(() -> new RuntimeException("Blog không tồn tại!"));
        CommentBlog comment = commentBlogMapper.toCmtBlog(request);
        comment.setUser(user);
        comment.setBlog(blog);
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(LocalDateTime.now());
        }
        commentBlogRepository.save(comment);
        return true;
    }

    @Override
    public Boolean updateCommentBlog(int id, UpdateCommentBlogRequest request) {
        CommentBlog comment = commentBlogRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setContent(request.getContent());
            commentBlogRepository.save(comment);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deteleCommentBlog(int id) {
        if (commentBlogRepository.existsById(id)) {
            commentBlogRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CommentBlogDTO> getAllCommentBlog(String query, int select) {
        List<CommentBlog> comments = commentBlogRepository.findAll();
        Collections.reverse(comments);
        return comments.stream()
                .map(commentBlogMapper::toCmtBlogDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentBlogDTO> getByBlog(int blogId) {
        Blog blog = blogReponsitory.findById(blogId).orElseThrow(
                () -> new AppException(ErrorCode.BLOG_NOT_FOUND)
        );
        List<CommentBlog> comments = commentBlogRepository.findByBlog(blog);
        Collections.reverse(comments);
        return comments.stream()
                .map(commentBlogMapper::toCmtBlogDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentBlogDTO> getByUser(String idUser) {
        User user = userReponsitory.findById(idUser).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        List<CommentBlog> comments = commentBlogRepository.findByUser(user);
        Collections.reverse(comments);
        return comments.stream()
                .map(commentBlogMapper::toCmtBlogDTO)
                .collect(Collectors.toList());
    }

}

