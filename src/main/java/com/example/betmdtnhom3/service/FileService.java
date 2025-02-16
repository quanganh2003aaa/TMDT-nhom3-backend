package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImpl {
    @Value("${fileUpload.rootPath}")
    private String rootPath;
    private Path root;

    private void init(){
        root = Paths.get(rootPath);
        if (Files.notExists(root)){
            try {
                Files.createDirectories(root);
            } catch (IOException e) {
                System.out.println("Error file: "+ e.getMessage());
            }
        }
    }

    @Override
    public boolean saveFile(MultipartFile file) {
        try {
            init();
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("Error file: "+ e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            init();
            Path filePath = root.resolve(fileName);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("Error deleting file: " + e.getMessage());
            return false;
        }
    }
}
