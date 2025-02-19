package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.MethodDeliveryDTO;
import com.example.betmdtnhom3.dto.request.CreateMethodDeliveryRequest;
import com.example.betmdtnhom3.dto.request.UpdateMethodDeliveryRequest;
import com.example.betmdtnhom3.entity.DeliveryMethod;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.MethodDeliveryMapper;
import com.example.betmdtnhom3.responsitory.DeliveryMethodReponsitory;
import com.example.betmdtnhom3.service.impl.DeliveryMethodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class DeliveryMethodService implements DeliveryMethodServiceImpl {
    @Autowired
    DeliveryMethodReponsitory deliveryMethodReponsitory;
    @Autowired
    MethodDeliveryMapper methodDeliveryMapper;
    @Override
    public boolean create(CreateMethodDeliveryRequest createMethodDeliveryRequest) {
        boolean isSuccess = false;
        try {
            DeliveryMethod deliveryMethod = methodDeliveryMapper.toCreateMethodDelivery(createMethodDeliveryRequest);
            deliveryMethodReponsitory.save(deliveryMethod);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_OTHER);
        }

        return isSuccess;
    }

    @Override
    public boolean update(int id, UpdateMethodDeliveryRequest updateMethodDeliveryRequest) {
        boolean isSuccess = false;
        DeliveryMethod deliveryMethod = deliveryMethodReponsitory.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.METHOD_DELIVERY_NOT_FOUND)
        );
        deliveryMethod.setName(updateMethodDeliveryRequest.getName());
        deliveryMethod.setPrice(updateMethodDeliveryRequest.getPrice());
        deliveryMethod.setInfo(updateMethodDeliveryRequest.getInfo());
        try {
            deliveryMethodReponsitory.save(deliveryMethod);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_OTHER);
        }
        return isSuccess;
    }

    @Override
    public boolean delete(int id) {
        boolean isSuccess = false;
        try {
            deliveryMethodReponsitory.deleteById(id);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_OTHER);
        }
        return isSuccess;
    }

    @Override
    public List<MethodDeliveryDTO> getAll() {
        List<MethodDeliveryDTO> methodDeliveryDTOList = new ArrayList<>();
        List<DeliveryMethod> deliveryMethodList = deliveryMethodReponsitory.findAll();
        for (DeliveryMethod method : deliveryMethodList) {
            MethodDeliveryDTO methodDeliveryDTO = methodDeliveryMapper.toMethodDeliveryDTO(method);
            methodDeliveryDTOList.add(methodDeliveryDTO);
        }
        return methodDeliveryDTOList;
    }

    @Override
    public MethodDeliveryDTO getById(int id) {
        DeliveryMethod deliveryMethod = deliveryMethodReponsitory.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.METHOD_DELIVERY_NOT_FOUND)
        );
        return methodDeliveryMapper.toMethodDeliveryDTO(deliveryMethod);
    }
}