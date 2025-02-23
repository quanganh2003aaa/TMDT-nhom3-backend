package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserReponsitory extends JpaRepository<User, String> {
    Optional<User> findByTel(String tel);
    Optional<User> findById(String id);
}
