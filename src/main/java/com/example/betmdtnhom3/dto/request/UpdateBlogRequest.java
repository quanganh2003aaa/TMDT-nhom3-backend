package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateBlogRequest {
    @NotNull(message = "Tiêu đề không được bỏ trống")
    @Size(min = 5, max = 100, message = "Tiêu đề phải từ 5 đến 100 ký tự")
    private String title;
    @NotNull(message = "Nội dung không được để trống")
    @Size(min = 20, message = "Nội dung phải có ít nhất 20 ký tự")
    private String content;

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
}
