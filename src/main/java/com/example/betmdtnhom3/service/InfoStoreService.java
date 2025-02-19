package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.request.CreateInfoStoreRequest;
import com.example.betmdtnhom3.dto.request.UpdateInfoStoreRequest;
import com.example.betmdtnhom3.dto.InfoStoreDTO;
import com.example.betmdtnhom3.entity.InfoStore;
import com.example.betmdtnhom3.entity.User;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.InfoStoreMapper;
import com.example.betmdtnhom3.responsitory.InfoStoreReponsitory;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.InfoStoreServicelmpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class InfoStoreService implements InfoStoreServicelmpl {
    @Autowired
    InfoStoreReponsitory infoStoreReponsitory;
    @Autowired
    InfoStoreMapper infoStoreMapper;
    @Autowired
    UserReponsitory userReponsitory;

    @Override
    public boolean create(CreateInfoStoreRequest createInfoStoreRequest) {
        boolean isSuccess = false;

        User user = userReponsitory.findById(createInfoStoreRequest.getUser()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        InfoStore infoStore = new InfoStore();
        infoStore.setTel(createInfoStoreRequest.getTel());
        infoStore.setAddress(createInfoStoreRequest.getAddress());
        infoStore.setUser(user);
        try {
            infoStoreReponsitory.save(infoStore);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }

        return isSuccess;
    }

    @Override
    public boolean update(int id, UpdateInfoStoreRequest updateInfoStoreRequest) {
        boolean isSuccess = false;
        InfoStore infoStore = infoStoreReponsitory.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.STORE_NOT_FOUND));

        User user = new User();
        user.setId(updateInfoStoreRequest.getUser());
        infoStore.setTel(updateInfoStoreRequest.getTel());
        infoStore.setAddress(updateInfoStoreRequest.getAddress());
        infoStore.setUser(user);
        try {
            infoStoreReponsitory.save(infoStore);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }

        return isSuccess;
    }

    @Override
    public boolean delete(int id) {
        boolean isSuccess = false;
        try {
            infoStoreReponsitory.deleteById(id);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.ERROR_OTHER);
        }
        return isSuccess;
    }

    @Override
    public List<InfoStoreDTO> getAll() {
        List<InfoStoreDTO> infoStoreDTOS = new ArrayList<>();
        List<InfoStore> infoStores = infoStoreReponsitory.findAll();
        for (InfoStore infoStore: infoStores) {
            InfoStoreDTO infoStoreDTO = infoStoreMapper.toInfoStoreDTO(infoStore);
            infoStoreDTOS.add(infoStoreDTO);
        }
        return infoStoreDTOS;
    }

}
