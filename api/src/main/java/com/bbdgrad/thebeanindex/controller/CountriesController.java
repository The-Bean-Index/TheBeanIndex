package com.bbdgrad.thebeanindex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bbdgrad.thebeanindex.repository.CountriesRepository;
import com.bbdgrad.thebeanindex.Dtos.CountryNamesDto;
import com.bbdgrad.thebeanindex.Dtos.CountriesDto;
import com.bbdgrad.thebeanindex.Dtos.CountriesResponseDto;
import com.bbdgrad.thebeanindex.model.Countries;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/countries")
public class CountriesController {
    
    CountriesRepository countriesRepository;

    @Autowired
    public CountriesController(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    @GetMapping("/all")
    public CountriesResponseDto getAllCountries() {
        List<Countries> countries = countriesRepository.findAll();
        List<CountriesDto> countriesDtoList = countries.stream().map(CountriesDto::toDto).collect(Collectors.toList());
        return new CountriesResponseDto(countriesDtoList);
    }

    @GetMapping("/names")
    public ResponseEntity<CountryNamesDto> getAllCountryNames() {
        List<String> countryNames = countriesRepository.findAll().stream()
                .map(Countries::getName)
                .map(Object::toString)
                .collect(Collectors.toList());

        CountryNamesDto countryNamesDto = new CountryNamesDto(countryNames);

        return new ResponseEntity<>(countryNamesDto, HttpStatus.OK);
    }
}
