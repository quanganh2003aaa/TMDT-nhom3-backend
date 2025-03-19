package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String name) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.createCategory(name));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.deleteCategory(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestParam String name) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.updateCategory(id, name));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAdmin(@RequestParam(defaultValue = "", required = false) String query,
                                         @RequestParam(defaultValue = "0", required = false) int select) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.getAll(query, select));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(categoryService.getById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
