package com.bbdgrad.thebeanindex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bbdgrad.thebeanindex.repository.BeansRepository;
import com.bbdgrad.thebeanindex.repository.CountriesRepository;
import com.bbdgrad.thebeanindex.repository.GdpRepository;
import com.bbdgrad.thebeanindex.repository.YearEnumRepository;
import com.bbdgrad.thebeanindex.Dtos.BeanNamesDto;
import com.bbdgrad.thebeanindex.Dtos.BeansDto;
import com.bbdgrad.thebeanindex.Dtos.BeansResponseDto;
import com.bbdgrad.thebeanindex.exception.BeanNotFoundException;
import com.bbdgrad.thebeanindex.exception.CountryNotFoundException;
import com.bbdgrad.thebeanindex.exception.GDPinBeanNotFoundException;
import com.bbdgrad.thebeanindex.exception.YearNotFoundException;
import com.bbdgrad.thebeanindex.model.Beans;
import com.bbdgrad.thebeanindex.model.Countries;
import com.bbdgrad.thebeanindex.model.GDP;
import com.bbdgrad.thebeanindex.model.YearEnum;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/beans")
public class BeansController {
    
    @Autowired
    BeansRepository beansRepository;

    @Autowired
    YearEnumRepository yearEnumRepository;

    @Autowired
    CountriesRepository countriesRepository;

    @Autowired
    GdpRepository gdpRepository;


    @GetMapping("/all")
    public BeansResponseDto getAllBeans() {
        List<Beans> beans = beansRepository.findAll();
        List<BeansDto> beansDtoList = beans.stream().map(BeansDto::toDto).collect(Collectors.toList());
        return new BeansResponseDto(beansDtoList);
    }

    @GetMapping("/names")
    public ResponseEntity<BeanNamesDto> getAllBeanNames() {
        List<String> beanNames = beansRepository.findAll().stream()
                .map(Beans::getName)
                .map(Object::toString)
                .collect(Collectors.toList());

        BeanNamesDto beanNamesDto = new BeanNamesDto(beanNames);

        return new ResponseEntity<>(beanNamesDto, HttpStatus.OK);
    }

    @GetMapping("/gdpOfCountryInTermsOfBeans/{countryName}/{beanName}/{year}")
    public ResponseEntity<?> getGDPofCountryInBeans(
        @PathVariable String countryName, 
        @PathVariable String beanName, 
        @PathVariable(name = "year") int yearValue
    ) {
        Optional<Countries> country = countriesRepository.findReferenceByName(countryName);
        Optional<Beans> bean = beansRepository.findReferenceByName(beanName);
        Optional<YearEnum> year = yearEnumRepository.findReferenceByYear(yearValue);
        Optional<GDP> gdp = gdpRepository
            .findAllReferencesByYearYearAndCountryName(yearValue, countryName);

        if (!country.isPresent()) throw new CountryNotFoundException(countryName);
        if (!bean.isPresent()) throw new BeanNotFoundException(beanName);
        if (!year.isPresent()) throw new YearNotFoundException(yearValue);
        if (!gdp.isPresent()) throw new GDPinBeanNotFoundException(countryName, yearValue);

        // GDPinBeanDto dto = GDPinBeanDto.toDto(gdp.get(), bean.get());
        
        return new ResponseEntity<>(gdp.get(), HttpStatus.ACCEPTED);
    
    }
}