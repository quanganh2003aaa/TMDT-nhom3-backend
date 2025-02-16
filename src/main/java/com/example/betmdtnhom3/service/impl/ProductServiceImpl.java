package com.example.betmdtnhom3.service.impl;

import com.example.betmdtnhom3.dto.ProductDTO;
import com.example.betmdtnhom3.dto.request.CreateProductRequest;
import com.example.betmdtnhom3.dto.request.UpdateProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceImpl {
    Boolean create(List<MultipartFile> file, CreateProductRequest createProductRequest);
    Boolean update(String idProduct,List<MultipartFile> file, UpdateProductRequest updateProductRequest);
    Boolean delete(String id);
//    ProductDTO getById(String id);
    List<ProductDTO> getAll(String query, int select) ;
//    List<ProductDTO> getAllAdmin(String query, int select) ;
//    List<ProductDTO> getIndex();
//    PagenationDTO getClothes(int page, int filterSort, int filterPrice);
//    PagenationDTO getAccessory(int page, int filterSort, int filterPrice);
//    PagenationDTO getProduct(int page, int filterSort, int filterPrice, String query);
//    PagenationDTO getSneaker(int page, int filterSort, int filterPrice);
//    long countProducts();
}
