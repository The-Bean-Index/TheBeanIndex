package com.bbdgrad.thebeanindex.Dtos;

import java.util.List;

public class CountriesResponseDto {
    private List<CountriesDto> Countries;

    public CountriesResponseDto(List<CountriesDto> countries) {
        this.Countries = countries;
    }

    public List<CountriesDto> getCountries() {
        return Countries;
    }

    public void setCountries(List<CountriesDto> countries) {
        this.Countries = countries;
    }
}