package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBrand(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(brandService.getAllBrands());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id,
                                    @RequestParam String name) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(brandService.update(id, name));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String name) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(brandService.create(name));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(brandService.delete(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id,
                                    @RequestParam String name) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(brandService.update(id, name));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
