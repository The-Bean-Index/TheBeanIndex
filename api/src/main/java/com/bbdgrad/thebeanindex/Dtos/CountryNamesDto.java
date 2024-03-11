package com.bbdgrad.thebeanindex.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class CountryNamesDto {
    private List<String> countryNames;

    public CountryNamesDto(List<String> countryNames) {
        this.countryNames = countryNames;
    }
}
