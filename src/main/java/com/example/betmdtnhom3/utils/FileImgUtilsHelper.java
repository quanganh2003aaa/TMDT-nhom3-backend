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
        // Lấy tất cả ảnh cũ của sản phẩm
        List<ImgProduct> oldImages = imgProductReponsitory.findAllByProduct(product);
        List<String> oldImageNames = oldImages.stream()
                .map(ImgProduct::getImg)
                .filter(img -> img != null)
                .collect(Collectors.toList());

        // Lấy tên ảnh mới từ danh sách MultipartFile
        List<String> newImageNames = newImages.stream()
                .map(MultipartFile::getOriginalFilename)
                .filter(img -> img != null && !img.isEmpty())
                .collect(Collectors.toList());

        // Tìm ảnh cần xóa (những ảnh không có trong newImages)
        List<String> imagesToDelete = oldImageNames.stream()
                .filter(img -> !newImageNames.contains(img))
                .collect(Collectors.toList());

        // Tìm ảnh cần thêm vào (những ảnh chưa có trong oldImages)
        List<MultipartFile> imagesToAdd = newImages.stream()
                .filter(img -> img.getOriginalFilename() != null && !oldImageNames.contains(img.getOriginalFilename()))
                .collect(Collectors.toList());

        // Xóa các ảnh không còn trong danh sách mới
        if (!imagesToDelete.isEmpty()) {
            imgProductReponsitory.deleteAllByImgIn(imagesToDelete);
            for (String img : imagesToDelete) {
                try {
                    fileService.deleteFile(img); // Xóa ảnh khỏi hệ thống
                } catch (Exception e) {
                    throw new AppException(ErrorCode.FILE_DELETE_ERROR);
                }
            }
        }

        // Biến để kiểm tra xem ảnh chính (index = 0) đã được thêm hay chưa
        boolean isFirstImageAdded = false;

        // Duyệt qua danh sách ảnh mới và lưu vào cơ sở dữ liệu
        for (int i = 0; i < imagesToAdd.size(); i++) {
            MultipartFile file = imagesToAdd.get(i);
            try {
                // Kiểm tra và lưu ảnh vào hệ thống
                if (fileService.saveFile(file)) {
                    ImgProduct imgProduct = new ImgProduct();
                    imgProduct.setProduct(product);
                    imgProduct.setImg(file.getOriginalFilename());

                    // Nếu ảnh chính chưa được thêm, gán order = 0 cho ảnh đầu tiên
                    if (!isFirstImageAdded) {
                        imgProduct.setIndexImg(0);  // Ảnh chính có index = 0
                        isFirstImageAdded = true; // Đánh dấu ảnh chính đã được thêm
                    } else {
                        imgProduct.setIndexImg(1); // Các ảnh còn lại có index = 1
                    }

                    // Lưu ảnh vào cơ sở dữ liệu
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
