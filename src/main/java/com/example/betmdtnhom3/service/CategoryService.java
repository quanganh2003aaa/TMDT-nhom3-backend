package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.CategoryDTO;
import com.example.betmdtnhom3.dto.request.CreateCategoryRequest;
import com.example.betmdtnhom3.dto.request.UpdateCategoryRequest;
import com.example.betmdtnhom3.entity.Category;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.CategoryMapper;
import com.example.betmdtnhom3.responsitory.CategoryReponsitory;
import com.example.betmdtnhom3.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService  implements CategoryServiceImpl {
    @Autowired
    CategoryReponsitory categoryReponsitory;
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public Boolean createCategory(CreateCategoryRequest createCategoryRequest) {
        boolean isSuccess = false;

        if (categoryReponsitory.existsById(String.valueOf(createCategoryRequest.getId()))) {
            throw new AppException(ErrorCode.CATEGORY_EXITED);
        }

        Category category = categoryMapper.toCategoryCreate(createCategoryRequest);
        category.setName(createCategoryRequest.getName());

        try {
            categoryReponsitory.save(category);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.CATEGORY_CREATE_ERROR);
        }

        return isSuccess;
    }

    @Override
    public Boolean updateCategory(int categoryId, UpdateCategoryRequest updateCategoryRequest) {
        boolean isSuccess = false;
        try {
            Category category = categoryReponsitory.findById(String.valueOf(categoryId))
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setName(updateCategoryRequest.getName());
            categoryReponsitory.save(category);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.CATEGORY_UPDATE_ERROR);
        }

        return isSuccess;
    }


    @Override
    public Boolean deleteCategory(String id) {
        boolean isSuccess = false;
        try {
            Category category = categoryReponsitory.findById(String.valueOf(Integer.parseInt(id)))
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            categoryReponsitory.deleteById(id);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.CATEGORY_DELETE_ERROR);
        }
        return isSuccess;
    }

    @Override
    public List<CategoryDTO> getAll(String query, int select) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<Category> categoryList = categoryReponsitory.findAll();
        for (Category category : categoryList) {
            CategoryDTO categoryDTO = categoryMapper.toCategoryDTO(category);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }
}
