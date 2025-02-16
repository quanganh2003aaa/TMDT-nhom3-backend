package com.example.betmdtnhom3.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImpl {
    boolean saveFile(MultipartFile multipartFile);
    boolean deleteFile(String fileName);
}
