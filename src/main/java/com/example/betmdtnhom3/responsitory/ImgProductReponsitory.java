package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.ImgProduct;
import com.example.betmdtnhom3.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgProductReponsitory extends JpaRepository<ImgProduct, String> {
    List<ImgProduct> findAllByProduct(Product product);
    void deleteAllByImgIn(List<String> images);
}
