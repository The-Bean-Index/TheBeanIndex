package com.bbdgrad.thebeanindex.Dtos;

import com.bbdgrad.thebeanindex.model.Countries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountriesDto {
    
    private Integer id;

    private String name;

    public static CountriesDto toDto(Countries countries) {
        CountriesDto dto = new CountriesDto(); 
        dto.setId(countries.getId());
        dto.setName(countries.getName());

        return dto;
    }
}
