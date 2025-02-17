package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.ApplyVoucherDTO;
import com.example.betmdtnhom3.dto.BrandDTO;
import com.example.betmdtnhom3.dto.VoucherDTO;
import com.example.betmdtnhom3.dto.request.ApplyVoucherRequest;
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

    public ApplyVoucherDTO applyVoucher(ApplyVoucherRequest applyVoucherRequest) {
        String id = applyVoucherRequest.getIdVoucher();
        int totalPrice = applyVoucherRequest.getTotalPrice();

        Voucher voucher = voucherRepository.findById(id).orElse(null);
        if (voucher == null) {
            return new ApplyVoucherDTO("Voucher không tồn tại", 0);
        }

        // Kiểm tra giá trị đơn hàng có đủ để sử dụng voucher không
        if (totalPrice < voucher.getMinOrderAmount()) {
            int neededAmount = voucher.getMinOrderAmount() - totalPrice;
            return new ApplyVoucherDTO("Bạn cần mua thêm " + neededAmount + " đ để sử dụng voucher", 0);
        }

        // Kiểm tra thời gian hiệu lực
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(voucher.getStartDate()) || (voucher.getEndDate() != null && now.isAfter(voucher.getEndDate()))) {
            return new ApplyVoucherDTO("Voucher đã hết hạn", 0);
        }

        // Kiểm tra số lần sử dụng còn lại
        if (voucher.getUsedCount() >= voucher.getMaxUsage()) {
            return new ApplyVoucherDTO("Voucher đã được sử dụng hết", 0);
        }

        // Nếu đủ điều kiện, cập nhật số lần sử dụng
        voucher.setUsedCount(voucher.getUsedCount() + 1);
        voucherRepository.save(voucher);

        return new ApplyVoucherDTO("Áp dụng voucher thành công!", voucher.getDiscountValue());
    }

}