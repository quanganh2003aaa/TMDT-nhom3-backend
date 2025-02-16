package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.BrandDTO;
import com.example.betmdtnhom3.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public List<BrandDTO> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Optional<BrandDTO> getBrandById(@PathVariable int id) {
        return brandService.getBrandById(id);
    }

    @PostMapping
    public BrandDTO createBrand(@RequestBody BrandDTO brandDTO) {
        return brandService.createBrand(brandDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteBrand(@PathVariable int id) {
        return brandService.deleteBrand(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable int id, @RequestBody BrandDTO dto) {
        Optional<BrandDTO> updatedBrand = brandService.updateBrand(id, dto);
        if (updatedBrand.isPresent()) {
            return ResponseEntity.ok(updatedBrand.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand không tồn tại");
        }
    }
}
