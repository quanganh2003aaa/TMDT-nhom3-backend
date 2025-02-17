package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.AddToCartRequest;
import com.example.betmdtnhom3.dto.request.UpdateCartRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.CartUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartUserController {
    @Autowired
    CartUserServiceImpl cartUserService;

    @GetMapping("/getByUser/{idUser}")
    public ResponseEntity<?> getAllBrand(@PathVariable String idUser){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(cartUserService.getByUser(idUser));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody @Valid AddToCartRequest addToCartRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(cartUserService.addToCart(addToCartRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String idUser,
                                    @RequestParam int idCart) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(cartUserService.deleteCart(idUser, idCart));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{idUser}")
    public ResponseEntity<?> update(@PathVariable String idUser,
                                    @RequestBody @Valid UpdateCartRequest updateCartRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(cartUserService.updateCart(idUser, updateCartRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
