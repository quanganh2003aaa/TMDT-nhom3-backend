package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UpdateRateRequest {
    @Size(max = 255, message = "CONTENT_RATE_ERROR")
    private String content;

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
