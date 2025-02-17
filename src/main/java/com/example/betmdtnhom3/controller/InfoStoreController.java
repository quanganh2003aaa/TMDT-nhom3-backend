package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.request.CreateInfoStoreRequest;
import com.example.betmdtnhom3.dto.request.UpdateInfoStoreRequest;
import com.example.betmdtnhom3.payload.ApiResponse;
import com.example.betmdtnhom3.service.impl.InfoStoreServicelmpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/infoStore")
public class InfoStoreController {

    @Autowired
    InfoStoreServicelmpl infoStoreService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute CreateInfoStoreRequest createInfoStoreRequest) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(infoStoreService.create(createInfoStoreRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(infoStoreService.delete(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id,
                                    @ModelAttribute UpdateInfoStoreRequest updateInfoStoreRequest) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(infoStoreService.update(id, updateInfoStoreRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAdmin() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(infoStoreService.getAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
