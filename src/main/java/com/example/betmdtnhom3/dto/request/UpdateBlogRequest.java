package com.example.betmdtnhom3.dto.request;

import com.example.betmdtnhom3.entity.CommentBlog;
import com.example.betmdtnhom3.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateBlogRequest {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private User user;
    private List<CommentBlog> comments;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CommentBlog> getComments() {
        return comments;
    }

    public void setComments(List<CommentBlog> comments) {
        this.comments = comments;
    }
}
