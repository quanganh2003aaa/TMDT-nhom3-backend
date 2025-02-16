package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReponsitory extends JpaRepository<User, String> {
    Optional<User> findByTel(String tel);
}
