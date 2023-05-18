package com.example.demo.dto;

import com.example.demo.entity.Image;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageDto {//implements Serializable {
    private long id;
    private String originalName;
    private String storedName;
    private String imageURL;
    private String uuid;
    private long fileSize;

    public static ImageDto createImageDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .originalName(image.getOriginalName())
                .storedName(image.getStoredName())
                .uuid(image.getUuid())
                .fileSize(image.getFileSize())
                .build();
    }

    public String getImageURL() {
        try {
            return URLEncoder.encode(imageURL+"/"+uuid+"_"+originalName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
