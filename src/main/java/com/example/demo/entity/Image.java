package com.example.demo.entity;

import com.example.demo.dto.ImageDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private Long id;
    @Column
    private String originalName;
    @Column
    private String storedName;
    @Column
    private String url;
    @Column
    private String uuid;
    @Column
    private String fileSize;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mark_id")
    private Mark mark;

    public static Image createImage(ImageDto imageDto, Mark mark) {
        return Image.builder()
                .id(imageDto.getId())
                .originalName(imageDto.getOriginalName())
                .storedName(imageDto.getStoredName())
                .url(imageDto.getUrl())
                .uuid(imageDto.getUuid())
                .fileSize(imageDto.getFileSize())
                .mark(mark)
                .build();
    }
}
