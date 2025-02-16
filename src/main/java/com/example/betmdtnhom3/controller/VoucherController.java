package com.example.betmdtnhom3.controller;

import com.example.betmdtnhom3.dto.VoucherDTO;
import com.example.betmdtnhom3.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/voucher")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public List<VoucherDTO> getAllVouchers() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable String id) {
        Optional<VoucherDTO> voucher = voucherService.getVoucherById(id);
        return voucher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public VoucherDTO createVoucher(@RequestBody VoucherDTO dto) {
        return voucherService.createVoucher(dto);
    }

    @PutMapping("/{id}")
    public VoucherDTO updateVoucher(@PathVariable String id, @RequestBody VoucherDTO voucherDTO) {
        return voucherService.updateVoucher(id, voucherDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable String id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyVoucher(
            @RequestParam String id,
            @RequestParam int totalPrice) {
        Map<String, Object> result = voucherService.applyVoucher(id, totalPrice);
        return ResponseEntity.ok(result);
    }

}