package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Min;

public class DetailOrderRequest {
    private String id;
    @Min(value = 1, message = "INVALID_QUANTITY_ORDER")
    private int quantity;
    private String size;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
