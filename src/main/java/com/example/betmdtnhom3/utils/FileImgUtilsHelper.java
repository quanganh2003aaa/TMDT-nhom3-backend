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
        List<String> oldImageNames = oldImages.stream()
                .map(ImgProduct::getImg)
                .filter(img -> img != null) // Tránh lỗi null
                .collect(Collectors.toList());

        List<String> newImageNames = newImages.stream()
                .map(MultipartFile::getOriginalFilename)
                .filter(img -> img != null && !img.isEmpty()) // Tránh null & rỗng
                .collect(Collectors.toList());

        List<String> imagesToDelete = oldImageNames.stream()
                .filter(img -> !newImageNames.contains(img))
                .collect(Collectors.toList());

        List<MultipartFile> imagesToAdd = newImages.stream()
                .filter(img -> img.getOriginalFilename() != null && !oldImageNames.contains(img.getOriginalFilename()))
                .collect(Collectors.toList());

        if (!imagesToDelete.isEmpty()) {
            imgProductReponsitory.deleteAllByImgIn(imagesToDelete);
            for (String img : imagesToDelete) {
                try {
                    fileService.deleteFile(img);
                } catch (Exception e) {
                    throw new AppException(ErrorCode.FILE_DELETE_ERROR);

                }
            }
        }

        for (MultipartFile file : imagesToAdd) {
            try {
                if (fileService.saveFile(file)) {
                    ImgProduct imgProduct = new ImgProduct();
                    imgProduct.setProduct(product);
                    imgProduct.setImg(file.getOriginalFilename());
                    imgProductReponsitory.save(imgProduct);
                } else {
                    throw new AppException(ErrorCode.FILE_UPLOAD_ERROR);
                }
            } catch (Exception e) {
                throw new AppException(ErrorCode.FILE_UPLOAD_ERROR);
            }
        }
    }

}
