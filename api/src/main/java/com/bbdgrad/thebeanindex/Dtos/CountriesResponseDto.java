package com.bbdgrad.thebeanindex.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class CountriesResponseDto {
    private List<CountriesDto> Countries;

    public CountriesResponseDto(List<CountriesDto> countries) {
        this.Countries = countries;
    }
}