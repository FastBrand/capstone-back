package com.example.demo.service.image;

import com.example.demo.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageDto> images();

    public ImageDto getImage(Long id);

    List<ImageDto> uploadImage(MultipartFile[] uploadFiles, String fileType);

}
