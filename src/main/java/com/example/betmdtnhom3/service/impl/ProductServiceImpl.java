package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.ProductDTO;
import com.example.betmdtnhom3.dto.ProductListDTO;
import com.example.betmdtnhom3.dto.request.CreateProductRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;
import com.example.betmdtnhom3.dto.request.UpdateProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceImpl {
    Boolean create(List<MultipartFile> file, CreateProductRequest createProductRequest);
    Boolean update(String idProduct,List<MultipartFile> file, UpdateProductRequest updateProductRequest);
    Boolean delete(String id);
    ProductDTO getById(String id);
    List<ProductListDTO> getAllAdmin(String query) ;
    List<ProductListDTO> getIndex();
    PagenationDTO getProduct(int page, int filterSort, int filterPrice, String query, String brand, String category);

    PagenationDTO getByCategory(int category, int page, int filterSort, int filterPrice);
    PagenationDTO getByBrand(int brand, int page, int filterSort, int filterPrice);
    long countProducts();
}
