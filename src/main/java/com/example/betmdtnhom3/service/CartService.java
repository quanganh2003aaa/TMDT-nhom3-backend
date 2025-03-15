package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.CartDTO;
import com.example.betmdtnhom3.dto.ProductCartDTO;
import com.example.betmdtnhom3.dto.request.AddToCartRequest;
import com.example.betmdtnhom3.dto.request.UpdateCartRequest;
import com.example.betmdtnhom3.entity.CartUser;
import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.entity.User;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.responsitory.CartReponsitory;
import com.example.betmdtnhom3.responsitory.ProductReponsitory;
import com.example.betmdtnhom3.responsitory.SizeReponsitory;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.CartUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements CartUserServiceImpl{
    @Autowired
    CartReponsitory cartReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    SizeReponsitory sizeReponsitory;

    @Override
    public boolean addToCart(AddToCartRequest addToCartRequest) {
        boolean isSuccess = false;

        User user = userReponsitory.findById(addToCartRequest.getIdUser()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = productReponsitory.findByIdAndSize(addToCartRequest.getIdProduct(), addToCartRequest.getSize());
        if (product == null){
            return isSuccess;
        }
        Optional<CartUser> cartUserExited = cartReponsitory.findByUserAndProductAndSize(user.getId(), product.getId(), addToCartRequest.getSize());
        if (cartUserExited.isEmpty()){
            try {
                CartUser cartUser = new CartUser();
                cartUser.setUser(user);
                cartUser.setProduct(product);
                cartUser.setQuantity(addToCartRequest.getQuantity());
                cartUser.setSize(addToCartRequest.getSize());
                cartReponsitory.save(cartUser);
                isSuccess = true;
            } catch (Exception e){
                throw new AppException(ErrorCode.ERROR_OTHER);
            }
        } else {
            try {
                cartUserExited.get().setQuantity(cartUserExited.get().getQuantity() + addToCartRequest.getQuantity());
                cartReponsitory.save(cartUserExited.get());
                isSuccess = true;
            } catch (Exception e) {
                throw new AppException(ErrorCode.ERROR_OTHER);
            }
        }

        return isSuccess;
    }

    @Override
    public boolean updateCart(String idUser, UpdateCartRequest updateCartRequest) {
        boolean isSuccess = false;

        User user = userReponsitory.findById(idUser).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        Product product = productReponsitory.findById(updateCartRequest.getIdProduct()).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        Optional<CartUser> cartUserExited = cartReponsitory.findByUserAndProductAndSize(idUser, updateCartRequest.getIdProduct(), updateCartRequest.getSize());

        if (cartUserExited.isPresent()){
            try {
                cartUserExited.get().setQuantity(updateCartRequest.getQuantity());
                cartUserExited.get().setSize(updateCartRequest.getSize());
                cartReponsitory.save(cartUserExited.get());
                isSuccess = true;
            } catch (Exception e){
                throw new AppException(ErrorCode.ERROR_OTHER);
            }
        }

        return isSuccess;
    }

    @Override
    public boolean deleteCart(String idUser, int idCart) {
        boolean isSuccess = false;

        User user = userReponsitory.findById(idUser).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        Optional<CartUser> cartUserExited = cartReponsitory.findByIdAndUser(idCart, user);
        if (cartUserExited.isPresent()){
            try {
                cartReponsitory.delete(cartUserExited.get());
                isSuccess = true;
            } catch (Exception e){
                throw new AppException(ErrorCode.ERROR_OTHER);
            }
        }

        return isSuccess;
    }

    @Override
    public CartDTO getByUser(String idUser) {
        User user = userReponsitory.findById(idUser).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        List<CartUser> cartUser = cartReponsitory.findByUser(user);
        List<ProductCartDTO> productCartDTOList = new ArrayList<>();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setIdUser(user.getId());
        int totalPrice = 0;
        for (CartUser cart:cartUser) {
            ProductCartDTO productCartDTO = new ProductCartDTO();
            productCartDTO.setId(cart.getProduct().getId());
            productCartDTO.setImg(String.valueOf(cart.getProduct().getImgProducts().get(0)));
            productCartDTO.setQuantity(cart.getQuantity());
            productCartDTO.setSize(cart.getSize());
            productCartDTO.setName(cart.getProduct().getName());
            productCartDTO.setPrice(cart.getProduct().getPrice());
            totalPrice += cart.getProduct().getPrice()*cart.getQuantity();
            productCartDTOList.add(productCartDTO);
        }
        cartDTO.setProductCartDTOList(productCartDTOList);
        cartDTO.setTotalPrice(totalPrice);
        return cartDTO;
    }
}
