package com.example.demo.dto;

import com.example.demo.entity.Mark;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class MarkDto {
    private Long id;
    private String brand_name;
    private String description;
    private String image;
    private String sector;
    private String type;
    private String poc;
    private String country;
    private String madrid;
    private String direct;
    private String status;
    private String total;


    public static MarkDto createMarkDto(Mark mark) {
        return MarkDto.builder()
                .id(mark.getId())
                .brand_name(mark.getBrand_name())
                .description(mark.getDescription())
                .image(mark.getImage())
                .sector(mark.getSector())
                .type(mark.getType())
                .poc(mark.getPoc())
                .country(mark.getCountry())
                .madrid(mark.getMadrid())
                .direct(mark.getDirect())
                .status(mark.getStatus())
                .total(mark.getTotal())
                .build();
    }

}
