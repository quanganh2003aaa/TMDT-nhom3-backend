package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.CommentBlogDTO;
import com.example.betmdtnhom3.dto.request.CreateCommentBlogRequest;
import com.example.betmdtnhom3.dto.request.UpdateCommentBlogRequest;
import com.example.betmdtnhom3.service.CommentBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentBlogController {
    @Autowired
    private CommentBlogService commentBlogService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentBlogRequest request) {
        boolean result = commentBlogService.createCommentBlog(request);
        if (result) {
            return ResponseEntity.ok("Tạo bình luận thành công!");
        }
        return ResponseEntity.badRequest().body("Lỗi tạo bình luận.");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable int id, @RequestBody UpdateCommentBlogRequest request) {
        boolean result = commentBlogService.updateCommentBlog(id, request);
        if (result) {
            return ResponseEntity.ok("Cập nhật bình luận thành công!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bình luận!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        boolean result = commentBlogService.deteleCommentBlog(id);
        if (result) {
            return ResponseEntity.ok("Xóa bình luận thành công!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bình luận!");
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<CommentBlogDTO>> getAllCommentBlog(
        @RequestParam(value = "query", required = false, defaultValue = "") String query,
        @RequestParam(value = "select", required = false, defaultValue = "0") int select) {
        List<CommentBlogDTO> comments = commentBlogService.getAllCommentBlog(query, select);
        return ResponseEntity.ok(comments);
    }

}
