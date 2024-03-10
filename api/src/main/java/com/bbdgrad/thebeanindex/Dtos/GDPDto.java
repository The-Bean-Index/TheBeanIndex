package com.bbdgrad.thebeanindex.Dtos;

import java.math.BigDecimal;

import com.bbdgrad.thebeanindex.model.GDP;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GDPDto {

    private Integer id;

    private CountriesDto country;

    private Integer yearId;

    private BigDecimal gdpAmount;

    public static GDPDto toDto(GDP gdp) {
        GDPDto dto = new GDPDto();
        dto.setId(gdp.getId());
        dto.setCountry(CountriesDto.toDto(gdp.getCountry()));
        dto.setYearId(gdp.getYearId());
        dto.setGdpAmount(gdp.getGdpAmount());

        return dto;
    }
}
