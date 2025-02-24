package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.BlogDTO;
import com.example.betmdtnhom3.dto.request.CreateBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateBlogRequest;
import com.example.betmdtnhom3.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createBlog(@RequestBody CreateBlogRequest request) {
        Boolean isCreated = blogService.createBlog(request);
        return ResponseEntity.ok(isCreated);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<BlogDTO>> getAllBlogs(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "select", required = false, defaultValue = "0") int select) {
        List<BlogDTO> blogs = blogService.getAllBlogs(query, select);
        return ResponseEntity.ok(blogs);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateBlog(@PathVariable int id, @RequestBody UpdateBlogRequest request) {
        Boolean isUpdated = blogService.updateBlog(id, request);
        return ResponseEntity.ok(isUpdated);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBlog(@PathVariable int id) {
        Boolean isDeleted = blogService.deleteBlog(id);
        return ResponseEntity.ok(isDeleted);
    }
}
