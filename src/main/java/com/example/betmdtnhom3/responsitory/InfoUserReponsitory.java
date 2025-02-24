package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.InfoUser;
import com.example.betmdtnhom3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfoUserReponsitory extends JpaRepository<InfoUser, String> {
    Optional<InfoUser> findByUser(User user);
    void deleteByUser(User user);
}
