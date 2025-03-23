package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.*;

public class CreateRateProductRequest {
    @Max(value = 5, message = "NUMBER_RATE_ERROR")
    @Min(value = 1, message = "NUMBER_RATE_ERROR")
    private int rate;
    @NotBlank(message = "CONTENT_RATE_EMPTY")
    @Size(min = 0, max = 255, message = "CONTENT_RATE_LENGTH_ERROR")
    private String content;
    @NotBlank(message = "USER_EMPTY")
    private String user;
    @NotBlank(message = "ID_PRODUCT_EMPTY")
    private String idProduct ;

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
