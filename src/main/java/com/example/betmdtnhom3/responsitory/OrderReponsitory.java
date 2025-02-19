package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Order;
import com.example.betmdtnhom3.entity.StatusOrder;
import com.example.betmdtnhom3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderReponsitory extends JpaRepository<Order, Integer> {
    Page<Order> findAllByStatusOrderNot(StatusOrder statusOrder, Pageable pageable);
    Page<Order> findAllByStatusOrder(StatusOrder statusOrders, Pageable pageable);
    List<Order> findAllByUserOrderByDateDesc(User user);
    @Query("SELECT o FROM orders o WHERE CAST(o.id AS string) LIKE %:partialId%")
    Page<Order> findByPartialIdOrder(@Param("partialId") String partialId, Pageable pageable);

    @Query("SELECT p FROM orders p WHERE CAST(p.id AS string) LIKE %:partialId% AND p.statusOrder.id = :statusOrder")
    Page<Order> findByPartialIdOrderAndStatusOrders(@Param("partialId") String partialId, @Param("statusOrder") int status, Pageable pageable);

}
