package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.RevenueDTO;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.RevenueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/revenue")
public class RevenueController {
    @Autowired
    RevenueServiceImpl revenueService;

    @GetMapping("/getChart")
    public ResponseEntity<?> getChart(){
        ApiResponse apiResponse = new ApiResponse();
        List<RevenueDTO> userDTOList = revenueService.getChart();
        apiResponse.setResult(userDTOList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getRevenue")
    public ResponseEntity<?> getRevenue(@RequestParam int month, @RequestParam int year){
        ApiResponse apiResponse = new ApiResponse();
        RevenueDTO userDTOList = revenueService.getByMonthAndYear(month, year);
        apiResponse.setResult(userDTOList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
