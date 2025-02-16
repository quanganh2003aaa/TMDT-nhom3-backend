package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.CategoryDTO;

import java.util.List;

public interface CategoryServiceImpl {
    Boolean createCategory(String cate);
    Boolean updateCategory(int categoryId, String cate);
    Boolean deleteCategory(int id);
    List<CategoryDTO> getAll(String query, int select);
}
