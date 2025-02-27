package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateBlogRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/create")
    public ResponseEntity<?> createBlog(@RequestBody CreateBlogRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.createBlog(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBlogs() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.getAllBlogs());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable int id, @RequestBody UpdateBlogRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.updateBlog(id, request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.deleteBlog(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.getById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
