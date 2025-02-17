package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.entity.RateProduct;
import com.example.betmdtnhom3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RateProductReponsitory extends JpaRepository<RateProduct, Integer> {
    Page<RateProduct> findAllByProduct(Product product, Pageable pageable);
    List<RateProduct> findAllByProductAndUser(Product product, User user);
    List<RateProduct> findAllByProduct(Product product);
}
