package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailOrderReponsitory extends JpaRepository<DetailOrder, Integer> {
}
