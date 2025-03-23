package com.example.betmdtnhom3.dto.request;

public class ChangeStatusRefundRequest {
    private String idUser;
    private int idOrder;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
}
