package com.bbdgrad.thebeanindex.Dtos;

import java.math.BigDecimal;

import com.bbdgrad.thebeanindex.model.Beans;
import com.bbdgrad.thebeanindex.model.GDP;
import com.bbdgrad.thebeanindex.model.GDPwithYear;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GDPinBeanDto {
    
    private String countryName;

    private int year;

    private String beanName;

    private BigDecimal gdpAmount;

    private BigDecimal beanAmount;

    private BigDecimal gdpInBeans;

    public static GDPinBeanDto toDto(GDPwithYear gdp, Beans beans) {
        GDPinBeanDto dto = new GDPinBeanDto();
        dto.setCountryName(gdp.getCountry().getName());
        dto.setYear(gdp.getYear().getYear());
        dto.setGdpAmount(gdp.getGdpAmount());
        dto.setBeanAmount(beans.getBeanPrice());
        dto.setBeanName(beans.getName());
        dto.setGdpInBeans(gdp.getGdpAmount().multiply(beans.getBeanPrice()));

        return dto;
    }
}
