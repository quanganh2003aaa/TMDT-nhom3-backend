package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Min;

public class AddToCartRequest {
    private String idUser;
    private String idProduct;
    @Min(value = 0, message = "Số lượng phải lớn hơn 0")
    private int quantity;
    private String size;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
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
