package com.example.betmdtnhom3.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class CommentBlogDTO {
    private int id;
    private String content;
    private LocalDateTime createdAt;
    private String userName;
    private int blogId;

    public CommentBlogDTO() {}

    public CommentBlogDTO(int id, String content, int blogId) {
        this.id = id;
        this.content = content;
        this.blogId = blogId;
    }
    public CommentBlogDTO(int id, String content, LocalDateTime createdAt, String userName, int blogId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.userName = userName;
        this.blogId = blogId;
    }
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
