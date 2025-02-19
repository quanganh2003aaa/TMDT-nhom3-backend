package com.example.betmdtnhom3.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class RateProductDTO {
    private int id;
    private int rate;
    private String content;
    private LocalDateTime createdDate;
    private String user;
    private String idProduct;
    private String nameProduct;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

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

    public String getNameProduct(){
        return nameProduct;
    }

    public void setNameProduct(String nameProduct){
        this.nameProduct = nameProduct;
    }

    public LocalDateTime getCreatedDate(){
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate){
        this.createdDate = createdDate;
    }
}
