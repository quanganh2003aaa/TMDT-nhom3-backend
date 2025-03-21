package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.OrderDTO;
import com.example.betmdtnhom3.dto.OrderGmailDTO;
import com.example.betmdtnhom3.dto.OrderListDTO;
import com.example.betmdtnhom3.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "statusOrder.name", target = "status")
    @Mapping(source = "user.name", target = "nameUser")
    @Mapping(source = "user.id", target = "user")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethod")
    @Mapping(source = "deliveryMethod.price", target = "shippingFee")
    @Mapping(source = "voucher.discountValue", target = "discountAmount")
    @Mapping(source = "details", target = "detailOrderDTOList")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    OrderDTO toOrder(Order order);

    @Mapping(source = "user.name", target = "nameUser")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethod")
    @Mapping(source = "deliveryMethod.price", target = "shippingFee")
    @Mapping(source = "voucher.discountValue", target = "discountAmount")
    @Mapping(source = "details", target = "detailOrderDTOList")
    OrderGmailDTO toOrderGmail(Order order);

    @Mapping(source = "statusOrder.name", target = "status")
    OrderListDTO toOderList(Order order);

}
