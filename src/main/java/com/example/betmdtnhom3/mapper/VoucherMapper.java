package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.VoucherDTO;
import com.example.betmdtnhom3.dto.request.CreateVoucherRequest;
import com.example.betmdtnhom3.entity.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    VoucherDTO toVoucherDTO(Voucher voucher);
    Voucher toVoucherCreate(CreateVoucherRequest createVoucherRequest);

}
