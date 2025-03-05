package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateCommentBlogRequest {
    @NotNull(message = "Nội dung không được để trống")
    @Size(min = 10, message = "Nội dung phải có ít nhất 10 ký tự")
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String user_id;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
