package com.example.demo.entity;

import com.example.demo.dto.ImageDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Seal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String originalName;

    @Column
    @NotNull
    private String storedName;

    @Column
    @NotNull
    private String imageURL;

    @Column
    @NotNull
    private String uuid;

    @Column
    private long fileSize;

    public static Seal createSeal(ImageDto imageDto) {
        return Seal.builder()
                .id(imageDto.getId())
                .originalName(imageDto.getOriginalName())
                .storedName(imageDto.getStoredName())
                .imageURL(imageDto.getImageURL())
                .uuid(imageDto.getUuid())
                .fileSize(imageDto.getFileSize())
                .build();
    }
}
