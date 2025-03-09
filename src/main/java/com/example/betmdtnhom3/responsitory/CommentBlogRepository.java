package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Blog;
import com.example.betmdtnhom3.entity.CommentBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentBlogRepository extends JpaRepository<CommentBlog, Integer> {
    List<CommentBlog> findByBlog(Blog blog);
}
