package com.example.demo.entity;

import com.example.demo.dto.CorporateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Corporate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "mid")
    private Mark mark;
    @Column
    @Size(max=64)
    @NotNull
    private String name_kor;
    @Column
    @Size(max=64)
    @NotNull
    private String name_eng;
    @Column
    @Size(max=64)
    @NotNull
    private String brn;
    @Column
    @Size(max=64)
    @NotNull
    private String crn;
    @Column
    @Size(max=64)
    @NotNull
    private String name;
    @Column
    @Size(max=64)
    @NotNull
    private String ssn;
    @Column
    @Size(max=64)
    private String mobile;
    @Column
    @Size(max=64)
    private String phone;
    @Column
    @Size(max=128)
    @NotNull
    private String email;
    @Column
    @Size(max=256)
    private String seal;
    @Column
    @Size(max=128)
    @NotNull
    private String address;
    @Column
    @Size(max=128)
    @NotNull
    private String detail;
    @Column
    @Size(max=64)
    @NotNull
    private String zipcode;
    @Column
    @Size(max=16)
    @NotNull
    private String agreement;


    public static Corporate createCorporate(CorporateDto dto, Mark mark) {
        return new Corporate(
                dto.getId(), mark, dto.getName_kor(), dto.getName_eng(), dto.getBrn(), dto.getCrn(), dto.getName(),
                dto.getSsn(), dto.getMobile(), dto.getPhone(), dto.getEmail(), dto.getSeal(), dto.getAddress(),
                dto.getDetail(), dto.getZipcode(), dto.getAgreement()
        );
    }

    public void patch(Corporate corporate) {
        if(corporate.name_kor != null)
            this.name_kor = corporate.getName_kor();
        if(corporate.name_eng != null)
            this.name_eng = corporate.getName_eng();
        if(corporate.brn != null)
            this.brn = corporate.getBrn();
        if(corporate.crn != null)
            this.crn = corporate.getCrn();
        if(corporate.name != null)
            this.name = corporate.getName();
        if(corporate.ssn != null)
            this.ssn = corporate.getSsn();
        if(corporate.mobile != null)
            this.mobile = corporate.getMobile();
        if(corporate.phone != null)
            this.phone = corporate.getPhone();
        if(corporate.email != null)
            this.email = corporate.getEmail();
        if(corporate.seal != null)
            this.seal = corporate.getSeal();
        if(corporate.address != null)
            this.address = corporate.getAddress();
        if(corporate.detail != null)
            this.detail = corporate.getDetail();
        if(corporate.zipcode != null)
            this.zipcode = corporate.getZipcode();
        if(corporate.agreement != null)
            this.agreement = corporate.getAgreement();
    }
}
