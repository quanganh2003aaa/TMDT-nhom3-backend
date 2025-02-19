package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.MethodDeliveryDTO;
import com.example.betmdtnhom3.dto.request.CreateMethodDeliveryRequest;
import com.example.betmdtnhom3.entity.DeliveryMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MethodDeliveryMapper {
    MethodDeliveryDTO toMethodDeliveryDTO (DeliveryMethod deliveryMethod);
    DeliveryMethod toCreateMethodDelivery(CreateMethodDeliveryRequest createMethodDeliveryRequest);
}
