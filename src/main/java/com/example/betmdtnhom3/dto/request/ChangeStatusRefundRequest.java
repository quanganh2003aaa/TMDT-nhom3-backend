package com.example.betmdtnhom3.dto.request;

public class ChangeStatusRefundRequest {
    private String idUser;
    private int idRefund;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdRefund() {
        return idRefund;
    }

    public void setIdRefund(int idRefund) {
        this.idRefund = idRefund;
    }
}
