package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.DetailOrderDTO;
import com.example.betmdtnhom3.entity.DetailOrder;
import com.example.betmdtnhom3.entity.ImgProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetailOrderMapper {
    @Mapping(source = "idProductHistory", target = "idProduct")
    @Mapping(source = "product.name", target = "nameProduct")
    @Mapping(target = "imgProduct", expression = "java(getFirstImage(detailOrder.getProduct().getImgProducts()))")
    @Mapping(source = "product.price", target = "price")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "quantity", target = "quantity")
    DetailOrderDTO toDTO(DetailOrder detailOrder);

    default String getFirstImage(List<ImgProduct> imgProducts) {
        return (imgProducts != null && !imgProducts.isEmpty()) ? imgProducts.get(0).getImg() : null;
    }

}
