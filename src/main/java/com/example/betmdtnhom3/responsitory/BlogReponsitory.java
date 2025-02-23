package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogReponsitory extends JpaRepository<Blog, Integer> {
    Optional<Blog> findById(Integer id);
}
