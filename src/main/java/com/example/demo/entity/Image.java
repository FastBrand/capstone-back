package com.example.demo.entity;

import com.example.demo.dto.ImageDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

//@EqualsAndHashCode
//@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Image {
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
    @Size(max=256) 
    @NotNull
    private String imageURL;

    @Column
    @NotNull
    private String uuid;

    @Column
    private long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mark_id")
    private Mark mark;

    public static Image createImage(ImageDto imageDto) {
        return Image.builder()
                .id(imageDto.getId())
                .originalName(imageDto.getOriginalName())
                .storedName(imageDto.getStoredName())
                .imageURL(imageDto.getImageURL())
                .uuid(imageDto.getUuid())
                .fileSize(imageDto.getFileSize())
                .build();
    }
}
