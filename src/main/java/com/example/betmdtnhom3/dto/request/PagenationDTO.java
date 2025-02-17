package com.example.betmdtnhom3.dto.request;

import java.util.List;

public class PagenationDTO {
    private int totalPages;
    private List<?> objectList;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<?> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<?> objectList) {
        this.objectList = objectList;
    }
}
