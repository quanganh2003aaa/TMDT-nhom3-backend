package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.dto.RateProductDTO;
import com.example.betmdtnhom3.dto.request.CreateRateProductRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;
import com.example.betmdtnhom3.dto.request.UpdateRateRequest;
import com.example.betmdtnhom3.entity.*;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.responsitory.DetailOrderReponsitory;
import com.example.betmdtnhom3.responsitory.ProductReponsitory;
import com.example.betmdtnhom3.responsitory.RateProductReponsitory;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.RateProductServiceImpl;
import com.example.betmdtnhom3.utils.DateUtilsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RateProductService implements RateProductServiceImpl{
    @Autowired
    RateProductReponsitory rateProductReponsitory;
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    DetailOrderReponsitory detailOrderReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    DateUtilsHelper dateUtilsHelper;

    @Override
    public boolean create(CreateRateProductRequest createRateProductRequest) {
        boolean isSuccess = false;
        StatusOrder statusOrder = new StatusOrder();
        statusOrder.setId(4);
        User user = userReponsitory.findById(createRateProductRequest.getUser()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<DetailOrder> checkUser = detailOrderReponsitory.findAllByProduct_IdAndOrder_UserAndOrder_StatusOrder
                (createRateProductRequest.getIdProduct(), user,statusOrder);
        if (checkUser.isEmpty()){
            throw new AppException(ErrorCode.USER_RATE_ERROR);
        }

        Product product = productReponsitory.findProductsById(createRateProductRequest.getIdProduct());
        List<RateProduct> checkRate = rateProductReponsitory.findAllByProductAndUser(product, user);
        if (!checkRate.isEmpty()){
            throw new AppException(ErrorCode.USER_RATE_EXISTED);
        }

        RateProduct rateProduct = new RateProduct();
        rateProduct.setRate(createRateProductRequest.getRate());
        rateProduct.setContent(createRateProductRequest.getContent());
        rateProduct.setCreatedDate(dateUtilsHelper.getNowLocalDateTime());

        rateProduct.setProduct(product);
        rateProduct.setUser(user);

        try {
            rateProductReponsitory.save(rateProduct);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.CREATE_RATE_ERROR);
        }

        return isSuccess;
    }

    @Override
    public PagenationDTO getByProduct(String idProduct, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdDate"));
        PagenationDTO pagenationDTO = new PagenationDTO();
        Product products = productReponsitory.findById(idProduct).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        Page<RateProduct> rateProductPage = rateProductReponsitory.findAllByProduct(products, pageable);

        List<RateProductDTO> rateProductDTOS = new ArrayList<>();
        for (RateProduct rateProduct: rateProductPage) {
            RateProductDTO rateProductDTO = new RateProductDTO();
            rateProductDTO.setRate(rateProduct.getRate());
            rateProductDTO.setContent(rateProduct.getContent());
            rateProductDTO.setCreatedDate(rateProduct.getCreatedDate());
            rateProductDTO.setIdProduct(rateProduct.getProduct().getId());
            rateProductDTO.setNameProduct(rateProduct.getProduct().getName());
            rateProductDTO.setUser(rateProduct.getUser().getName());

            rateProductDTOS.add(rateProductDTO);
        }
        pagenationDTO.setTotalPages(rateProductPage.getTotalPages());
        pagenationDTO.setObjectList(rateProductDTOS);

        return pagenationDTO;
    }

    @Override
    public boolean update(int id, UpdateRateRequest updateRateRequest){
        RateProduct rateProduct = rateProductReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.RATE_NOT_FOUND));

        if(updateRateRequest.getContent() != null){
            rateProduct.setContent(updateRateRequest.getContent());
        }

        try{
            rateProductReponsitory.save(rateProduct);
            return true;
        } catch (Exception e){
            throw new AppException(ErrorCode.UPDATE_RATE_ERROR);
        }
    }

    @Override
    public boolean delete(int id){
        RateProduct rateProduct = rateProductReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.RATE_NOT_FOUND));

        try{
            rateProductReponsitory.delete(rateProduct);
            return true;
        } catch (Exception e){
            throw new AppException(ErrorCode.DELETE_RATE_ERROR);
        }
    }

    @Override
    public PagenationDTO getAllRates(int page){
        Pageable pageable = PageRequest.of(page - 1,  5, Sort.by(Sort.Direction.DESC, "createdDate"));
        PagenationDTO pagenationDTO = new PagenationDTO();

        Page<RateProduct> rateProductPage = rateProductReponsitory.findAll(pageable);

        List<RateProductDTO> rateProductDTOS = new ArrayList<>();
        for(RateProduct rateProduct : rateProductPage){
            RateProductDTO rateProductDTO = new RateProductDTO();
            rateProductDTO.setRate(rateProduct.getRate());
            rateProductDTO.setContent(rateProduct.getContent());
            rateProductDTO.setCreatedDate(rateProduct.getCreatedDate());
            rateProductDTO.setIdProduct(rateProduct.getProduct().getId());
            rateProductDTO.setNameProduct(rateProduct.getProduct().getName());
            rateProductDTO.setUser(rateProduct.getUser().getName());

            rateProductDTOS.add(rateProductDTO);
        }
        pagenationDTO.setTotalPages(rateProductPage.getTotalPages());
        pagenationDTO.setObjectList(rateProductDTOS);

        return pagenationDTO;
    }
}
