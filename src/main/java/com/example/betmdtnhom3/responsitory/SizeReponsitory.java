package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SizeReponsitory extends JpaRepository<Size, Integer> {
    List<Size> findAllByProduct(Product product);
}
