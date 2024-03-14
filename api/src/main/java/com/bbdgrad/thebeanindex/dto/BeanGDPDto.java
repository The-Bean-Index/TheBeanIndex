package com.bbdgrad.thebeanindex.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BeanGDPDto {

    private String country;
    private int year;
    private BigDecimal gdpAmount;

    public BeanGDPDto(String country, int year, BigDecimal gdpAmount) {
        this.country = country;
        this.year = year;
        this.gdpAmount = gdpAmount;
    }

}
