package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.request.CreateProductRequest;
import com.example.betmdtnhom3.dto.request.UpdateProductRequest;
import com.example.betmdtnhom3.entity.*;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.ProductMapper;
import com.example.betmdtnhom3.responsitory.ImgProductReponsitory;
import com.example.betmdtnhom3.responsitory.ProductReponsitory;
import com.example.betmdtnhom3.responsitory.SizeReponsitory;
import com.example.betmdtnhom3.service.impl.FileServiceImpl;
import com.example.betmdtnhom3.service.impl.ProductServiceImpl;
import com.example.betmdtnhom3.utils.FileImgUtilsHelper;
import com.example.betmdtnhom3.utils.SizeUtilsHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements ProductServiceImpl {
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    FileServiceImpl fileService;
    @Autowired
    SizeUtilsHelper sizeUtilsHelper;
    @Autowired
    SizeReponsitory sizeReponsitory;
    @Autowired
    ImgProductReponsitory imgProductReponsitory;
    @Autowired
    FileImgUtilsHelper fileImgUtilsHelper;

    @Override
    @Transactional
    public Boolean create(List<MultipartFile> files, CreateProductRequest createProductRequest) {
        boolean isSuccess = false;
        if (productReponsitory.existsById(createProductRequest.getId())) {
            throw new AppException(ErrorCode.PRODUCT_EXITED);
        }

        Product product = productMapper.toProductCreate(createProductRequest);

        try {
            productReponsitory.save(product);

            List<ImgProduct> imgProducts = new ArrayList<>();

            for (MultipartFile file : files) {
                boolean isSaved = fileService.saveFile(file);
                if (!isSaved) {
                    throw new AppException(ErrorCode.FILE_UPLOAD_ERROR);
                }

                ImgProduct imgProduct = new ImgProduct();
                imgProduct.setProduct(product);
                imgProduct.setImg(file.getOriginalFilename());
                imgProducts.add(imgProduct);
            }

            imgProductReponsitory.saveAll(imgProducts);

            List<String> sizes = sizeUtilsHelper.parseSizes(createProductRequest.getSize());
            for (String size : sizes) {
                Size sizeEntity = new Size();
                sizeEntity.setSize(size);
                sizeEntity.setProduct(product);
                sizeReponsitory.save(sizeEntity);
            }

            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.PRODUCT_CREATE_ERROR);
        }

        return isSuccess;
    }

    @Override
    @Transactional
    public Boolean update(String idProduct, List<MultipartFile> files, UpdateProductRequest updateProductRequest) {
        boolean isSuccess = false;
        try {
            Product product = productReponsitory.findById(idProduct)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            sizeUtilsHelper.updateSizes(product, updateProductRequest.getSize());
            fileImgUtilsHelper.updateProductImages(product, files);

            Category category = new Category();
            category.setId(updateProductRequest.getCategory());
            Brand brand = new Brand();
            brand.setId(updateProductRequest.getBrand());
            product.setBrand(brand);
            product.setCategory(category);
            product.setName(updateProductRequest.getName());
            product.setPrice(updateProductRequest.getPrice());
            product.setDescription(updateProductRequest.getDescription());
            product.setQuantity(updateProductRequest.getQuantity());

            productReponsitory.save(product);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.PRODUCT_UPDATE_ERROR);
        }

        return isSuccess;
    }

    @Override
    public Boolean delete(String id) {
        boolean isSuccess = false;
        try {
            Product product = productReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
            List<ImgProduct> imgProducts = imgProductReponsitory.findAllByProduct(product);
            for (ImgProduct img: imgProducts) {
                fileService.deleteFile(img.getImg());
            }
            imgProductReponsitory.deleteAll(imgProducts);
            productReponsitory.delete(product);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.PRODUCT_DELETE_ERROR);
        }

        return isSuccess;
    }
}
