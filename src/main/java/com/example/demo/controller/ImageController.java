package com.example.demo.controller;

import com.example.demo.dto.ImageDto;
import com.example.demo.service.image.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageServiceImpl imageService;

    @PostMapping("/upload")
    public ResponseEntity<List<ImageDto>> uploadImage(@RequestBody MultipartFile[] uploadFiles) throws IOException {
        List<ImageDto> imageDtoList = imageService.uploadImage(uploadFiles);
        return ResponseEntity.status(HttpStatus.OK).body(imageDtoList);
    }

}
