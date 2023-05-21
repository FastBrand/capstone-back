package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.image.ImageServiceImpl;
import com.example.demo.service.info.InfoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RegisterController {
    private final InfoServiceImpl infoService;
    private final ImageServiceImpl imageService;

    @PostMapping(value = "/personal", consumes = "multipart/form-data")
    //public ResponseEntity<InfoDto> createPersonal(@RequestBody InfoDto dto, MultipartFile[] files) {
    public ResponseEntity<InfoDto> createPersonal(@RequestPart("data") InfoDto dto, @RequestPart("image") MultipartFile[] files) {
        List<ImageDto> imageDtoList = imageService.uploadImage(files);
        InfoDto infoDto = infoService.createPer(dto);
        return ResponseEntity.status(HttpStatus.OK).body(infoDto);
    }

    @PostMapping("/corporate")
    public ResponseEntity<InfoDto> createCorporate(@RequestBody InfoDto dto, MultipartFile[] files) {
        List<ImageDto> imageDtoList = imageService.uploadImage(files);
        InfoDto infoDto = infoService.createCorp(dto);
        return ResponseEntity.status(HttpStatus.OK).body(infoDto);
    }
}
