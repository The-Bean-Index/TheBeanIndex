package com.bbdgrad.thebeanindex.Dtos;

import java.util.List;

public class CountryNamesDto {
    private List<String> countryNames;

    public CountryNamesDto(List<String> countryNames) {
        this.countryNames = countryNames;
    }

    public List<String> getCountryNames() {
        return countryNames;
    }

    public void setCountryNames(List<String> countryNames) {
        this.countryNames = countryNames;
    }
}
