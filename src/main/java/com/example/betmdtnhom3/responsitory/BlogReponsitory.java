package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogReponsitory extends JpaRepository<Blog, String> {
}
