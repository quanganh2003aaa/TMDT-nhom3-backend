package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueReponsitory extends JpaRepository<Revenue, Integer> {
    Revenue findRevenuesByMonthAndYear(int month, int year);
}
