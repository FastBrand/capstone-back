package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.image.ImageServiceImpl;
import com.example.demo.service.info.InfoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RegisterController {
    private final InfoServiceImpl infoService;
    private final ImageServiceImpl imageService;

    @PostMapping("/all/personal")
    //public ResponseEntity<InfoDto> createPersonal(@RequestBody InfoDto dto, MultipartFile[] files) {
    public ResponseEntity<InfoDto> createAllPersonal(@RequestPart("data") InfoDto dto, @RequestPart("image") MultipartFile[] files) {
        List<ImageDto> imageDtoList = imageService.uploadImage(files, "image");
        InfoDto infoDto = infoService.createPer(dto);
        return ResponseEntity.status(HttpStatus.OK).body(infoDto);
    }

    @PostMapping("/all/corporate")
    public ResponseEntity<InfoDto> createAllCorporate(@RequestPart("data") InfoDto dto, @RequestPart("image") MultipartFile[] images, @RequestPart("seal") MultipartFile[] seals) {
        List<ImageDto> imageDtoList = imageService.uploadImage(images, "image");
        List<ImageDto> sealDtoList = imageService.uploadImage(seals, "seal");
        InfoDto infoDto = infoService.createCorp(dto);
        return ResponseEntity.status(HttpStatus.OK).body(infoDto);
    }

    @PostMapping("/personal")
    public ResponseEntity<InfoDto> createPersonal(@RequestBody InfoDto dto) {
        InfoDto infoDto = infoService.createPer(dto);
        return ResponseEntity.status(HttpStatus.OK).body(infoDto);
    }

    @PostMapping("/corporate")
    public ResponseEntity<InfoDto> createCorporate(@RequestBody InfoDto dto) {
        InfoDto infoDto = infoService.createCorp(dto);
        return ResponseEntity.status(HttpStatus.OK).body(infoDto);
    }
}
