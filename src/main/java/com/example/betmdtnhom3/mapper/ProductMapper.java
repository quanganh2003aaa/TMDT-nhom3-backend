package com.example.betmdtnhom3.mapper;

import com.example.betmdtnhom3.dto.ImgProductDTO;
import com.example.betmdtnhom3.dto.ProductDTO;
import com.example.betmdtnhom3.dto.ProductListDTO;
import com.example.betmdtnhom3.dto.request.CreateProductRequest;
import com.example.betmdtnhom3.entity.Brand;
import com.example.betmdtnhom3.entity.Category;
import com.example.betmdtnhom3.entity.ImgProduct;
import com.example.betmdtnhom3.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "brand.name", target = "brand")
    ProductDTO toProductDTO(Product product);

    @Mapping(source = "category", target = "category", qualifiedByName = "IntToCategory")
    @Mapping(source = "brand", target = "brand", qualifiedByName = "IntToBrand")
    Product toProductCreate(CreateProductRequest createProductRequest);

    ImgProductDTO toImgProductDTO(ImgProduct imgProduct);

//    @Mapping(source = "indexImg", target = "indexImg")
    ProductListDTO toProductListDTO(Product product);
    @Named("IntToCategory")
    default Category IntToCategory(Integer cate) {
        if (cate == null) {
            return null;
        }
        Category category = new Category();
        category.setId(cate);
        return category;
    }

    @Named("IntToBrand")
    default Brand IntToBrand(Integer brand) {
        if (brand == null) {
            return null;
        }
        Brand brand1 = new Brand();
        brand1.setId(brand);
        return brand1;
    }
}
