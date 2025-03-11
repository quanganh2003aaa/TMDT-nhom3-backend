package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateProductRequest;
import com.example.betmdtnhom3.dto.request.UpdateProductRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.responsitory.ProductReponsitory;
import com.example.betmdtnhom3.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
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
                                    @ModelAttribute @Valid CreateProductRequest createProductRequest){

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
        System.out.println(1);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.update(id, files, updateProductRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/admin/getAll")
    public ResponseEntity<?> getAllAdmin(@RequestParam(defaultValue = "", required = false) String query,
                                         @RequestParam(defaultValue = "0", required = false) int select){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.getAllAdmin(query, select));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getCategory")
    public ResponseEntity<?> getCategory(@RequestParam(defaultValue = "1", required = false) int category,
                                        @RequestParam(defaultValue = "1", required = false) int page,
                                        @RequestParam(defaultValue = "0", required = false) int filterSort,
                                        @RequestParam(defaultValue = "0", required = false) int filterPrice){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.getByCategory(category, page, filterSort, filterPrice));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getBrand")
    public ResponseEntity<?> getBrand(@RequestParam(defaultValue = "1", required = false) int brand,
                                         @RequestParam(defaultValue = "1", required = false) int page,
                                         @RequestParam(defaultValue = "0", required = false) int filterSort,
                                         @RequestParam(defaultValue = "0", required = false) int filterPrice){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.getByBrand(brand, page, filterSort, filterPrice));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getProduct")
    public ResponseEntity<?> getProduct(@RequestParam(defaultValue = "1", required = false) int page,
                                        @RequestParam(defaultValue = "0", required = false) int filterSort,
                                        @RequestParam(defaultValue = "0", required = false) int filterPrice,
                                        @RequestParam(defaultValue = "", required = false) String query){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.getProduct(page, filterSort, filterPrice, query));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.getById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getIndex")
    public ResponseEntity<?> getIndex(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.getIndex());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        long count = productService.countProducts();
        return ResponseEntity.ok(count);
    }
}
