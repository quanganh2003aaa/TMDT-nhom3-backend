package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.Enum.Role;
import com.example.betmdtnhom3.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReponsitory extends JpaRepository<User, String> {
    Optional<User> findByGmail(String gmail);
    boolean existsByGmail(String gmail);
    Optional<User> findByTel(String tel);

    Optional<User> findById(String id);
    List<User> findAllByRole(Role role);
    Optional<User> findByGmailAndOtp(String gmail, String otp);
}
