package com.example.demo.service.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String upload(Long id, MultipartFile file, String fileType) throws IOException;
}
