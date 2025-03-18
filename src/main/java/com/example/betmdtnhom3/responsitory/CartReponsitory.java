package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.dto.ProductCartDTO;
import com.example.betmdtnhom3.entity.CartUser;
import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.entity.Size;
import com.example.betmdtnhom3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartReponsitory extends JpaRepository<CartUser, Integer> {
    List<CartUser> findByUser(User user);
    Optional<CartUser> findByIdAndUser(int id, User user);
    @Query("SELECT c FROM cart_user c WHERE c.user.id = :user AND c.product.id = :product AND c.size = :size")
    Optional<CartUser> findByUserAndProductAndSize(@Param("user") String userId,
                                                   @Param("product") String productId,
                                                   @Param("size") String size);
    List<CartUser> findAllByUser(User user);
    boolean deleteAllByUser(User user);
}
