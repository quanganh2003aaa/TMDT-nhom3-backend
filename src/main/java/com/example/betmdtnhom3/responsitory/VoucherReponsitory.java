package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherReponsitory extends JpaRepository<Voucher, String> {
}