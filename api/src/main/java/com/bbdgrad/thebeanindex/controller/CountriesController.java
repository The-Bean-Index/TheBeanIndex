package com.bbdgrad.thebeanindex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbdgrad.thebeanindex.repository.CountriesRepository;
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

    @GetMapping("/names")
    public List<String> getAllCountryNames() {
        List<Countries> countries = countriesRepository.findAll();

        return countries.stream()
                .map(Countries::getName)
                .collect(Collectors.toList());
    }
}
