package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Min;
public class CreateMethodDeliveryRequest {
    private String name;
    @Min(value = 0, message = "Giá trị của phương thức phải lớn hơn 0")
    private int price;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
