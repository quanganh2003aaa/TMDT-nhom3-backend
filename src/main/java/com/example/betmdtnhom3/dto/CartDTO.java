package com.example.betmdtnhom3.dto;

import java.util.List;

public class CartDTO {
    private String idUser;
    private int totalPrice;
    private List<ProductCartDTO> productCartDTOList;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductCartDTO> getProductCartDTOList() {
        return productCartDTOList;
    }

    public void setProductCartDTOList(List<ProductCartDTO> productCartDTOList) {
        this.productCartDTOList = productCartDTOList;
    }
}
