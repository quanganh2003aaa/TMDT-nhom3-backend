package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.CategoryDTO;
import com.example.betmdtnhom3.dto.request.CreateCategoryRequest;
import com.example.betmdtnhom3.dto.request.UpdateCategoryRequest;
import com.example.betmdtnhom3.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryServiceImpl {
    Boolean createCategory(CreateCategoryRequest createCategoryRequest);
    Boolean updateCategory(int categoryId, UpdateCategoryRequest updateCategoryRequest);
    Boolean deleteCategory(String id);
    List<CategoryDTO> getAll(String query, int select);
}
