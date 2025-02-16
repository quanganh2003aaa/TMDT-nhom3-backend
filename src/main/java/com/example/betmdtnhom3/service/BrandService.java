package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.dto.BrandDTO;
import com.example.betmdtnhom3.entity.Brand;
import com.example.betmdtnhom3.responsitory.BrandReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {
    @Autowired
    private BrandReponsitory brandRepository;

    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<BrandDTO> getBrandById(int id) {
        return brandRepository.findById(id).map(this::convertToDTO);
    }

    public BrandDTO createBrand(BrandDTO dto) {
        Brand brand = new Brand();
        brand.setName(dto.getName());
        return convertToDTO(brandRepository.save(brand));
    }

    public String deleteBrand(int id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isEmpty()) {
            return "Brand không tồn tại";
        }

        Brand brand = brandOptional.get();
        String brandName = brand.getName();
        brandRepository.deleteById(id);

        return "Xóa Brand " + brandName + " thành công";
    }

    private BrandDTO convertToDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());

        // Kiểm tra null trước khi gọi stream()
        if (brand.getProducts() != null) {
            dto.setProducts(brand.getProducts().stream()
                    .map(product -> product.getName()) // Lấy tên sản phẩm hoặc ID
                    .collect(Collectors.toList()));
        } else {
            dto.setProducts(new ArrayList<>()); // Tránh NullPointerException
        }

        return dto;
    }

    public Optional<BrandDTO> updateBrand(int id, BrandDTO dto) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isEmpty()) {
            return Optional.empty(); // Trả về Optional rỗng nếu không tìm thấy Brand
        }

        Brand brand = brandOptional.get();
        brand.setName(dto.getName()); // Cập nhật tên Brand nếu có

        // Lưu thay đổi vào database
        brand = brandRepository.save(brand);

        return Optional.of(convertToDTO(brand)); // Trả về DTO sau khi cập nhật
    }

}
