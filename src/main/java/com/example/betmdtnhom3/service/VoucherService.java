package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.BrandDTO;
import com.example.betmdtnhom3.dto.VoucherDTO;
import com.example.betmdtnhom3.dto.request.CreateVoucherRequest;
import com.example.betmdtnhom3.dto.request.UpdateVoucherRequest;
import com.example.betmdtnhom3.entity.Brand;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.VoucherMapper;
import com.example.betmdtnhom3.responsitory.VoucherReponsitory;
import com.example.betmdtnhom3.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoucherService {
    @Autowired
    private VoucherReponsitory voucherRepository;
    @Autowired
    VoucherMapper voucherMapper;

    public List<VoucherDTO> getAllVouchers() {
        List<VoucherDTO> voucherDTOList = new ArrayList<>();
        List<Voucher> voucherList = voucherRepository.findAll();
        for (Voucher voucher:voucherList) {
            VoucherDTO voucherDTO = voucherMapper.toVoucherDTO(voucher);
            voucherDTOList.add(voucherDTO);
        }
        return voucherDTOList;
    }

    public VoucherDTO getVoucherById(String id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.VOUCHER_NOT_FOUND)
        );
        return voucherMapper.toVoucherDTO(voucher);
    }

    public boolean createVoucher(CreateVoucherRequest createVoucherRequest) {
        boolean isSuccess = false;

        try {
            Voucher voucher = voucherMapper.toVoucherCreate(createVoucherRequest);
            voucher.setUsedCount(0);
            voucherRepository.save(voucher);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }

        return isSuccess;
    }

    public boolean updateVoucher(String id, UpdateVoucherRequest updateVoucherRequest) {
        boolean isSuccess = false;
        Voucher voucher = voucherRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.VOUCHER_NOT_FOUND)
        );

        voucher.setId(updateVoucherRequest.getId());
        voucher.setDiscountValue(updateVoucherRequest.getDiscountValue());
        voucher.setMinOrderAmount(updateVoucherRequest.getMinOrderAmount());
        voucher.setStartDate(updateVoucherRequest.getStartDate());
        voucher.setEndDate(updateVoucherRequest.getEndDate());
        voucher.setMaxUsage(updateVoucherRequest.getMaxUsage());
        voucher.setUsedCount(updateVoucherRequest.getUsedCount());

        try {
            voucherRepository.save(voucher);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }

        return isSuccess;
    }

    public boolean deleteVoucher(String id) {
        boolean isSuccess = false;
        try {
            voucherRepository.deleteById(id);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }
        return isSuccess;
    }


}