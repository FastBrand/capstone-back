package com.example.demo.service.image;

import com.example.demo.dto.ImageDto;
import com.example.demo.dto.InfoDto;
import com.example.demo.dto.MarkDto;
import com.example.demo.entity.Image;
import com.example.demo.entity.Mark;
import com.example.demo.entity.Seal;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.MarkRepository;
import com.example.demo.repository.SealRepository;
import com.example.demo.service.File.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final SealRepository sealRepository;
    private final MarkRepository markRepository;
    private final FileServiceImpl fileService;
    private String uploadDir = System.getProperty("user.dir") + "/Image";

    @Override
    public List<ImageDto> images() {
        return imageRepository.findAll().stream()
                .map(ImageDto::createImageDto)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDto getImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error"));
        return ImageDto.createImageDto(image);
    }

    public List<Image> getImagesByMarkId(Long markId) {
        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new IllegalArgumentException("Mark not found with id: " + markId));

        List<Image> images = mark.getImages();
        return images;
    }

    @Override
    public List<ImageDto> uploadImage(InfoDto dto, MultipartFile[] uploadFiles, String fileType) { //throws Exception {
        List<ImageDto> resultDtoList = new ArrayList<>();
        if (uploadFiles == null) {
            return resultDtoList;
        }

        for (MultipartFile uploadFile: uploadFiles) {
            if (!uploadFile.isEmpty()) {
                long fileSize = uploadFile.getSize();
                String originalName = StringUtils.cleanPath(uploadFile.getOriginalFilename());
                String uuid = UUID.randomUUID().toString();
                String fileExtension = originalName.substring(originalName.lastIndexOf("."));
                String fileName = uuid + "_" + originalName.replace(fileExtension, "") + fileExtension;
            /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String currentDate = simpleDateFormat.format(new Date());*/

                //String filePath = uploadDir + "/" + fileName;
                Long markId = dto.getMark().getId();

                try {
                    String filePath = fileService.uploadFile(markId, uploadFile);

                    ImageDto imageDto = ImageDto.builder()
                            .originalName(originalName)
                            .storedName(fileName)
                            .uuid(uuid)
                            .imageURL(filePath)
                            .fileSize(fileSize)
                            .build();

                    Mark mark = Mark.createMark(dto.getMark());

                    if (fileType.equals("image")) {
                        Image image = Image.createImage(imageDto);

                        imageRepository.save(image);
                    }
                    else if (fileType.equals("seal")) {
                        Seal seal = Seal.createSeal(imageDto);
                        sealRepository.save(seal);
                    }

                    resultDtoList.add(imageDto);
                } catch (IOException e) {
                    //e.printStackTrace();
                    return resultDtoList;
                }
            }
        }
        return resultDtoList;
    }

    private void storeFile(MultipartFile file, String filePath) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path storedFilePath = uploadPath.resolve(filePath);
        file.transferTo(storedFilePath.toFile());
    }

}
