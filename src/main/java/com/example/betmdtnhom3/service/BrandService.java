package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.BrandDTO;
import com.example.betmdtnhom3.entity.Brand;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.BrandMapper;
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
    @Autowired
    BrandMapper brandMapper;

    public List<BrandDTO> getAllBrands() {
        List<BrandDTO> brandDTOList = new ArrayList<>();
        List<Brand> brandList = brandRepository.findAll();
        for (Brand brand:brandList) {
            BrandDTO brandDTO = brandMapper.toBrandDTO(brand);
            brandDTOList.add(brandDTO);
        }
        return brandDTOList;
    }

    public BrandDTO getBrandById(int id) {
        Brand brand = brandRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.BRAND_NOT_FOUND)
        );
        return brandMapper.toBrandDTO(brand);
    }

    public boolean create(String name) {
        boolean isSuccess = false;

        try {
            Brand brand = new Brand();
            brand.setName(name);
            brandRepository.save(brand);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }

        return isSuccess;
    }

    public boolean delete(int id) {
        boolean isSuccess = false;
        try {
            brandRepository.deleteById(id);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }
        return isSuccess;
    }

    public boolean update(int id, String name) {
        boolean isSuccess = false;
        Brand brand = brandRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        brand.setName(name);
        try {
            brandRepository.save(brand);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }
        return isSuccess;
    }

}
