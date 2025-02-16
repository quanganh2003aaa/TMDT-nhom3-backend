package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandReponsitory extends JpaRepository<Brand, Integer> {
}
