package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReponsitory extends JpaRepository<Products, Integer> {
}
