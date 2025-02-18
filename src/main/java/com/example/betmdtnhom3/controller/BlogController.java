package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateBlogRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/blog")
public class BlogController {
    @Autowired
    BlogServiceImpl blogService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateBlogRequest createBlogRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.createBlog(createBlogRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.deteleBlog(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody UpdateBlogRequest updateBlogRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.updateBlog(id, updateBlogRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/admin/getAll")
    public ResponseEntity<?> getAllAdmin(@RequestParam(defaultValue = "", required = false) String query,
                                         @RequestParam(defaultValue = "0", required = false) int select) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(blogService.getAll(query, select));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
