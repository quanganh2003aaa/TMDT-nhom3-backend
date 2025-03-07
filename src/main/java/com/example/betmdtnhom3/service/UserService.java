package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.Enum.Role;
import com.example.betmdtnhom3.dto.UserDTO;
import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.SignUpRequest;
import com.example.betmdtnhom3.dto.request.UpdateUserRequest;
import com.example.betmdtnhom3.entity.InfoUser;
import com.example.betmdtnhom3.entity.User;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.UserMapper;
import com.example.betmdtnhom3.responsitory.InfoUserReponsitory;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceImpl {
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    InfoUserReponsitory infoUserReponsitory;
    @Autowired
    UserMapper userMapper;

    @Override
    public Boolean createUser(SignUpRequest signUpRequest){
        return createUserWithRole(signUpRequest, Role.USER);
    }

    @Override
    public Boolean createAdmin(SignUpRequest signUpRequest){
        return createUserWithRole(signUpRequest, Role.ADMIN);
    }

    public Boolean createUserWithRole(SignUpRequest signUpRequest, Role role) {
        boolean isSuccess = false;

        Optional<User> usersExisted = userReponsitory.findByTel(signUpRequest.getTel());
        if (usersExisted.isEmpty()){
            User user = new User();
            user.setName(signUpRequest.getName());

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setTel(signUpRequest.getTel());
            user.setGmail(signUpRequest.getGmail());
            user.setRole(role);
            try {
                userReponsitory.save(user);
                isSuccess = true;
            } catch (Exception e){
                throw new AppException(ErrorCode.ERROR_OTHER);
            }
        }else {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return isSuccess;
    }

    @Override
    public UserDTO login(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userReponsitory.findByTel(authenticationRequest.getTel())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticate = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticate){
            throw new AppException(ErrorCode.AUTHENTICATED);
        }

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(String id, UpdateUserRequest request){
        User user = userReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setName(request.getName());
        user.setTel(request.getTel());

        InfoUser infoUser = infoUserReponsitory.findByUser(user).orElse(new InfoUser());

        if (infoUser.getId() == null){
            infoUser.setUser(user);
        }

        infoUser.setCity(request.getCity());
        infoUser.setDistrict(request.getDistrict());
        infoUser.setWard(request.getWard());

        infoUserReponsitory.save(infoUser);
        userReponsitory.save(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateAdmin(String id, UpdateUserRequest request){
        User user = userReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setName(request.getName());
        user.setTel(request.getTel());
        InfoUser infoUser = infoUserReponsitory.findByUser(user).orElse(new InfoUser());

        if (infoUser.getId() == null){
            infoUser.setUser(user);
        }

        infoUser.setCity(request.getCity());
        infoUser.setDistrict(request.getDistrict());
        infoUser.setWard(request.getWard());

        infoUserReponsitory.save(infoUser);
        userReponsitory.save(user);
        return userMapper.toUserDTO(user);
    }


    @Override
    public boolean deleteUser(String id){
        boolean isSuccess = false;
        User user = userReponsitory.findById(id).orElseThrow(
                ()->new AppException(ErrorCode.USER_NOT_FOUND)
        );
        try {
            userReponsitory.delete(user);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.DELETE_USER_ERROR);
        }

        userReponsitory.delete(user);
        return true;
    }

    @Override
    public List<UserDTO> getAllUsers(){
        return userReponsitory.findAll().stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }
}
