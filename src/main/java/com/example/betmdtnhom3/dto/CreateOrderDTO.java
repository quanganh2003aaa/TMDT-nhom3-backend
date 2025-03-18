package com.example.betmdtnhom3.dto;

public class CreateOrderDTO {
    private int idOrder;
    private boolean isSuccess;

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
