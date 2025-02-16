package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.Enum.Role;
import com.example.betmdtnhom3.dto.UserDTO;
import com.example.betmdtnhom3.dto.request.AuthenticationRequest;
import com.example.betmdtnhom3.dto.request.SignUpRequest;
import com.example.betmdtnhom3.entity.User;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.UserMapper;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService implements UserServiceImpl {
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    UserMapper userMapper;

    @Override
    public Boolean createUser(SignUpRequest signUpRequest){
        boolean isSucces = false;

        Optional<User> userExisted = userReponsitory.findByTel(signUpRequest.getTel());
        if(!userExisted.isPresent()){
            User user = new User();
            user.setName(signUpRequest.getName());

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword((passwordEncoder.encode(signUpRequest.getPassword())));
            user.setTel(signUpRequest.getTel());
            user.setGmail(signUpRequest.getGmail());
            user.setRole(Role.USER);
            try{
                userReponsitory.save(user);
                isSucces = true;
            } catch (Exception e){
                throw new AppException(ErrorCode.ERROR_OTHER);
            }
        } else {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return isSucces;
    }

    @Override
    public UserDTO login(AuthenticationRequest authenticationRequest){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userReponsitory.findByTel(authenticationRequest.getTel()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticate = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!authenticate){
            throw new AppException(ErrorCode.AUTHENTICATED);
        }

        return userMapper.toUserDTO(user);
    }
}
