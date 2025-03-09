package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenReponsitory extends JpaRepository<InvalidatedToken, String> {
}
