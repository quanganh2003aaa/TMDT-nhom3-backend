package com.example.betmdtnhom3.utils;

import com.example.betmdtnhom3.dto.ApplyVoucherDTO;
import com.example.betmdtnhom3.dto.request.ApplyVoucherRequest;
import com.example.betmdtnhom3.entity.Voucher;
import com.example.betmdtnhom3.mapper.VoucherMapper;
import com.example.betmdtnhom3.responsitory.VoucherReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class VoucherUtilsHelper {
    @Autowired
    private VoucherReponsitory voucherRepository;
    @Autowired
    VoucherMapper voucherMapper;
    public ApplyVoucherDTO applyVoucher(Voucher voucher, int totalPrice) {
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
