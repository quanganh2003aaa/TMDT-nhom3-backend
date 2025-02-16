package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.dto.VoucherDTO;
import com.example.betmdtnhom3.responsitory.VoucherReponsitory;
import com.example.betmdtnhom3.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherService {
    @Autowired
    private VoucherReponsitory voucherRepository;

    public List<VoucherDTO> getAllVouchers() {
        return voucherRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<VoucherDTO> getVoucherById(String id) {
        return voucherRepository.findById(id).map(this::convertToDTO);
    }

    public VoucherDTO createVoucher(VoucherDTO dto) {
        Voucher voucher = convertToEntity(dto);
        return convertToDTO(voucherRepository.save(voucher));
    }

    public VoucherDTO updateVoucher(String id, VoucherDTO dto) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (optionalVoucher.isEmpty()) {
            throw new RuntimeException("Voucher không tồn tại");
        }

        Voucher voucher = optionalVoucher.get();
        voucher.setDiscountValue(dto.getDiscountValue());
        voucher.setMinOrderAmount(dto.getMinOrderAmount());
        voucher.setStartDate(dto.getStartDate());
        voucher.setEndDate(dto.getEndDate());
        voucher.setMaxUsage(dto.getMaxUsage());
        voucher.setUsedCount(dto.getUsedCount());

        return convertToDTO(voucherRepository.save(voucher));
    }

    public void deleteVoucher(String id) {
        voucherRepository.deleteById(id);
    }

    private VoucherDTO convertToDTO(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();
        dto.setId(voucher.getId());
        dto.setDiscountValue(voucher.getDiscountValue());
        dto.setMinOrderAmount(voucher.getMinOrderAmount());
        dto.setStartDate(voucher.getStartDate());
        dto.setEndDate(voucher.getEndDate());
        dto.setMaxUsage(voucher.getMaxUsage());
        dto.setUsedCount(voucher.getUsedCount());
        return dto;
    }

    private Voucher convertToEntity(VoucherDTO dto) {
        Voucher voucher = new Voucher();
        voucher.setId(dto.getId());
        voucher.setDiscountValue(dto.getDiscountValue());
        voucher.setMinOrderAmount(dto.getMinOrderAmount());
        voucher.setStartDate(dto.getStartDate());
        voucher.setEndDate(dto.getEndDate());
        voucher.setMaxUsage(dto.getMaxUsage());
        voucher.setUsedCount(dto.getUsedCount());
        return voucher;
    }

    public Map<String, Object> applyVoucher(String id, int totalPrice) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (optionalVoucher.isEmpty()) {
            response.put("status", "FALSE");
            response.put("message", "Voucher không tồn tại");
            return response;
        }

        Voucher voucher = optionalVoucher.get();

        // Kiểm tra điều kiện 1: totalPrice >= minOrderAmount
        if (totalPrice < voucher.getMinOrderAmount()) {
            int neededAmount = voucher.getMinOrderAmount() - totalPrice;
            response.put("status", "FALSE");
            response.put("message", "Bạn cần mua thêm " + neededAmount + " đ để sử dụng voucher");
            return response;
        }

        // Kiểm tra điều kiện 2: Thời gian hợp lệ
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(voucher.getStartDate()) || (voucher.getEndDate() != null && now.isAfter(voucher.getEndDate()))) {
            response.put("status", "FALSE");
            response.put("message", "Voucher đã hết hạn");
            return response;
        }

        // Kiểm tra điều kiện 3: Số lần sử dụng còn lại
        if (voucher.getUsedCount() >= voucher.getMaxUsage()) {
            response.put("status", "FALSE");
            response.put("message", "Voucher đã được sử dụng hết");
            return response;
        }

        // Nếu tất cả điều kiện thỏa mãn, tăng usedCount và lưu lại
        voucher.setUsedCount(voucher.getUsedCount() + 1);
        voucherRepository.save(voucher);

        response.put("status", "TRUE");
        response.put("discountValue", voucher.getDiscountValue());
        return response;
    }
}