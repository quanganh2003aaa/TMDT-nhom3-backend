package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryMethodReponsitory extends JpaRepository<DeliveryMethod, Integer> {
}
