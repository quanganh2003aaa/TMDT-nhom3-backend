package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, Integer> {
}
