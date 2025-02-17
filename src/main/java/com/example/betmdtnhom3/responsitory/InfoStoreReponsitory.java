package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.entity.InfoStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InfoStoreReponsitory extends JpaRepository<InfoStore, Integer> {
}
