package com.example.betmdtnhom3.utils;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.entity.ImgProduct;
import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.responsitory.ImgProductReponsitory;
import com.example.betmdtnhom3.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileImgUtilsHelper {
    @Autowired
    private ImgProductReponsitory imgProductReponsitory;
    @Autowired
    private FileService fileService;

    public void updateProductImages(Product product, List<MultipartFile> newImages) {
        List<ImgProduct> oldImages = imgProductReponsitory.findAllByProduct(product);
        imgProductReponsitory.deleteAll(oldImages);
        saveProductImages(newImages, product);
    }

    public void saveProductImages(List<MultipartFile> files, Product product) {
        List<ImgProduct> imgProducts = new ArrayList<>();

        int indexImg = 0;
        for (MultipartFile file : files) {
            if (!fileService.saveFile(file)) {
                throw new AppException(ErrorCode.FILE_UPLOAD_ERROR);
            }

            ImgProduct imgProduct = new ImgProduct();
            imgProduct.setProduct(product);
            imgProduct.setImg(file.getOriginalFilename());
            if (indexImg == 0) {
                imgProduct.setIndexImg(0);
                indexImg++;
            } else {
                imgProduct.setIndexImg(1);
            }
            imgProducts.add(imgProduct);
        }

        imgProductReponsitory.saveAll(imgProducts);
    }

}
