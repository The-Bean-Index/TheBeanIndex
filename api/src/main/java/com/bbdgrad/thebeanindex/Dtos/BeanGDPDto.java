package com.bbdgrad.thebeanindex.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BeanGDPDto {

    private String country;
    private int year;
    private BigDecimal convertedGDP;

    public BeanGDPDto(String country, int year, BigDecimal convertedGDP) {
        this.country = country;
        this.year = year;
        this.convertedGDP = convertedGDP;
    }

}
