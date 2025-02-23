package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogReponsitory extends JpaRepository<Blog, Integer> {
    Optional<Blog> findById(Integer id);
}
