package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.DetailOrder;
<<<<<<< HEAD
import com.example.betmdtnhom3.entity.Order;
import com.example.betmdtnhom3.entity.StatusOrder;
import com.example.betmdtnhom3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailOrderReponsitory extends JpaRepository<DetailOrder, Integer> {
    List<DetailOrder> findAllByOrder(Order order);
    List<DetailOrder> findAllByProduct_IdAndOrder_UserAndOrder_StatusOrder(String product, User user, StatusOrder statusOrder);
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailOrderReponsitory extends JpaRepository<DetailOrder, Integer> {
>>>>>>> origin/main
}
