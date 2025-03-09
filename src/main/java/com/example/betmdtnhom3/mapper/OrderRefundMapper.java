package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.OrderRefundDTO;
import com.example.betmdtnhom3.entity.OrderRefund;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderRefundMapper {
    @Mapping(source = "reason.name", target = "reason")
    @Mapping(source = "order.id", target = "idOrder")
    @Mapping(source = "order.statusOrder.name", target = "status")
    OrderRefundDTO toOderRefundDTO(OrderRefund orderRefund);
}
