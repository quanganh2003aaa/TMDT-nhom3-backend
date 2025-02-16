package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateProductRequest;
import com.example.betmdtnhom3.dto.request.UpdateProductRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.responsitory.ProductReponsitory;
import com.example.betmdtnhom3.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductServiceImpl productService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("files") List<MultipartFile> files,
                                    @ModelAttribute CreateProductRequest createProductRequest){

//        CreateProductRequest createProductRequest = new CreateProductRequest();
//        createProductRequest.setId(id);
//        createProductRequest.setName(name);
//        createProductRequest.setSize(size);
//        createProductRequest.setPrice(price);
//        createProductRequest.setQuantity(quantity);
//        createProductRequest.setDescription(description);
//        createProductRequest.setCategory(category);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.create(files, createProductRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.delete(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @RequestParam("files") List<MultipartFile> files,
                                    @ModelAttribute UpdateProductRequest updateProductRequest){

//        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
//        updateProductRequest.setName(name);
//        updateProductRequest.setSize(size);
//        updateProductRequest.setPrice(price);
//        updateProductRequest.setQuantity(quantity);
//        updateProductRequest.setDescription(description);
//        updateProductRequest.setCategory(category);
//        if (file != null && !file.isEmpty()) {
//            updateProductRequest.setImg(file.getOriginalFilename());
//        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.update(id, files, updateProductRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
}
