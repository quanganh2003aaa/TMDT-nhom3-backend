package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeReponsitory extends JpaRepository<Size, Integer> {
    List<Size> findAllByProduct(Product product);
}
