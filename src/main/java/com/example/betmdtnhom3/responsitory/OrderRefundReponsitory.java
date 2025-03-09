package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.OrderRefund;
import com.example.betmdtnhom3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRefundReponsitory extends JpaRepository<OrderRefund, Integer> {
    @Query("SELECT o FROM order_refund o WHERE CAST(o.id AS string) LIKE %:partialId%")
    Page<OrderRefund> findByPartialIdOrderRefund(@Param("partialId") String partialId, Pageable pageable);

    @Query("SELECT p FROM order_refund p WHERE CAST(p.id AS string) LIKE %:partialId% AND p.order.statusOrder.id = :statusOrder")
    Page<OrderRefund> findByPartialIdOrderAndStatusOrders(@Param("partialId") String partialId, @Param("statusOrder") int status, Pageable pageable);

    Optional<OrderRefund> findByIdAndOrderUser(int id, User user);
}
