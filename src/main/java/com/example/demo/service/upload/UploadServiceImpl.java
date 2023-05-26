package com.example.demo.service.upload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;
    private final AmazonS3 amazonS3;

    @Override
    public String upload(Long id, MultipartFile file, String fileType) throws IOException {

        String originalName = fileType.equals("image") ? "marks/" + file.getOriginalFilename()
                : "seals/" + file.getOriginalFilename();
        long size = file.getSize();

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(size);

        amazonS3.putObject(
                new PutObjectRequest(S3Bucket, originalName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        String imageUrl = amazonS3.getUrl(S3Bucket, originalName).toString();

        return imageUrl;
    }
}
