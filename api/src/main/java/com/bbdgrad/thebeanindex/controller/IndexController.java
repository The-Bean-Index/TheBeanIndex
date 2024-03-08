package com.bbdgrad.thebeanindex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbdgrad.thebeanindex.repository.*;
import com.bbdgrad.thebeanindex.model.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class IndexController {

    CountriesRepository countriesRepository;

    @Autowired
    public IndexController(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }
    
    @GetMapping
    public List<Countries> test() {
        return countriesRepository.findAll();
    }
}
