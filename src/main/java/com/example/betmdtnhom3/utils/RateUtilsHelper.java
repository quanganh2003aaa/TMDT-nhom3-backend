package com.example.betmdtnhom3.utils;

import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.entity.RateProduct;
import com.example.betmdtnhom3.responsitory.RateProductReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RateUtilsHelper {
    @Autowired
    RateProductReponsitory rateProductReponsitory;

    public Double getRate(Product product) {
        List<RateProduct> rateProductList = rateProductReponsitory.findAllByProduct(product);
        int totalRate = 0;
        for (RateProduct rateList : rateProductList) {
            totalRate += rateList.getRate();
        }
        return totalRate / (double) rateProductList.size();
    }
}