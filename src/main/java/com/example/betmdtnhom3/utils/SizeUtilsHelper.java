package com.example.betmdtnhom3.utils;

import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.entity.Size;
import com.example.betmdtnhom3.responsitory.SizeReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SizeUtilsHelper {
    @Autowired
    SizeReponsitory sizeReponsitory;
    public List<String> parseSizes(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(size -> !size.isEmpty()) // Bỏ qua các size rỗng (nếu có)
                .collect(Collectors.toList());
    }


    public boolean isSizeAvailable(List<Size> sizes, String targetSize) {
        if (targetSize == null || targetSize.trim().isEmpty()) {
            throw new IllegalArgumentException("Target size cannot be null or empty");
        }

        return sizes.stream()
                .anyMatch(size -> size.getSize().equalsIgnoreCase(targetSize.trim()));
    }

    public List<Size> getSizesByProduct(Product product) {
        return sizeReponsitory.findAllByProduct(product);
    }

    public void updateSizes(Product product, String newSizeString) {
        List<Size> existingSizes = getSizesByProduct(product);
        List<String> newSizes = parseSizes(newSizeString);

        List<Size> sizesToDelete = existingSizes.stream()
                .filter(size -> !newSizes.contains(size.getSize()))
                .collect(Collectors.toList());

        List<Size> sizesToAdd = newSizes.stream()
                .filter(size -> existingSizes.stream().noneMatch(s -> s.getSize().equals(size)))
                .map(size -> {
                    Size newSize = new Size();
                    newSize.setSize(size);
                    newSize.setProduct(product);
                    return newSize;
                }).collect(Collectors.toList());

        if (!sizesToDelete.isEmpty()) {
            sizeReponsitory.deleteAll(sizesToDelete);
        }

        if (!sizesToAdd.isEmpty()) {
            sizeReponsitory.saveAll(sizesToAdd);
        }
    }
}
