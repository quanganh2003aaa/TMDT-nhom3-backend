package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.dto.RevenueDTO;
import com.example.betmdtnhom3.entity.Revenue;
import com.example.betmdtnhom3.mapper.RevenueMapper;
import com.example.betmdtnhom3.responsitory.RevenueReponsitory;
import com.example.betmdtnhom3.service.impl.RevenueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RevenueService implements RevenueServiceImpl {
    @Autowired
    RevenueReponsitory revenueReponsitory;
    @Autowired
    RevenueMapper revenueMapper;

    @Override
    public List<RevenueDTO> getChart() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        Page<Revenue> revenues = revenueReponsitory.findAll(pageable);
        List<Revenue> reversedList = new ArrayList<>(revenues.getContent());
        Collections.reverse(reversedList);
        List<RevenueDTO> revenueDTOList = new ArrayList<>();
        for (Revenue revenue:reversedList) {
            RevenueDTO revenueDTO = revenueMapper.toRevenue(revenue);
            revenueDTOList.add(revenueDTO);
        }

        return revenueDTOList;
    }

    @Override
    public RevenueDTO getByMonthAndYear(int month, int year) {
        Revenue revenues = revenueReponsitory.findRevenuesByMonthAndYear(month, year);
        return revenueMapper.toRevenue(revenues);
    }
}
