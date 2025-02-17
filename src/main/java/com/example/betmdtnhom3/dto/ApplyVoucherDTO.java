package com.example.betmdtnhom3.dto;

public class ApplyVoucherDTO {
    private String notify;
    private int value;

    public ApplyVoucherDTO(String notify, int value) {
        this.notify = notify;
        this.value = value;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
