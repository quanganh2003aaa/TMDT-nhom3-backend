package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CreateRateProductRequest {
    @Max(value = 5, message = "NUMBER_RATE_ERROR")
    @Min(value = 1, message = "NUMBER_RATE_ERROR")
    private int rate;
    @Size(max = 255, message = "CONTENT_RATE_ERROR")
    private String content;
    @NotEmpty
    private String user;
    @NotEmpty
    private String idProduct;

    public int getRate(){
        return rate;
    }

    public void setRate(int rate){
        this.rate = rate;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }

    public String getIdProduct(){
        return idProduct;
    }

    public void setIdProduct(String idProduct){
        this.idProduct = idProduct;
    }
}
