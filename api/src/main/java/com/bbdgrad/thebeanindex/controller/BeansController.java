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
import com.bbdgrad.thebeanindex.dtos.BeanGDPDto;
import com.bbdgrad.thebeanindex.dtos.BeanNamesDto;
import com.bbdgrad.thebeanindex.dtos.BeansDto;
import com.bbdgrad.thebeanindex.dtos.BeansResponseDto;
import com.bbdgrad.thebeanindex.dtos.GDPDto;
import com.bbdgrad.thebeanindex.dtos.GDPRatioDto;
import com.bbdgrad.thebeanindex.exception.BeanNotFoundException;
import com.bbdgrad.thebeanindex.exception.CountryNotFoundException;
import com.bbdgrad.thebeanindex.exception.GDPinBeanNotFoundException;
import com.bbdgrad.thebeanindex.exception.YearNotFoundException;
import com.bbdgrad.thebeanindex.model.Beans;
import com.bbdgrad.thebeanindex.model.Countries;
import com.bbdgrad.thebeanindex.model.GDP;
import com.bbdgrad.thebeanindex.model.YearEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public ResponseEntity<BeanGDPDto> getGDPOfCountryInBeans( 
            @PathVariable String countryName,
            @PathVariable String beanName,
            @PathVariable(name = "year") int yearValue
    ) {
        Optional<GDP> gdp = gdpRepository.findAllReferencesByYearYearAndCountryName(yearValue, countryName);
        Optional<Beans> bean = beansRepository.findReferenceByName(beanName);

        if (!gdp.isPresent()) throw new GDPinBeanNotFoundException(countryName, yearValue);
        if (!bean.isPresent()) throw new IllegalArgumentException("Bean not found: " + beanName);

        BigDecimal beanValue = bean.get().getBeanPrice();

        if (beanValue.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }

        BigDecimal gdpAmount = gdp.get().getGdpAmount();

        BigDecimal beanGDP = gdpAmount.divide(beanValue, 2, RoundingMode.HALF_UP);

        BeanGDPDto dto = new BeanGDPDto(countryName, yearValue, beanGDP.setScale(2, RoundingMode.HALF_UP));

        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/gdpRatio/{countryName1}/{countryName2}/{beanName}/{year}")
    public ResponseEntity<GDPRatioDto> getGDPRatio(
            @PathVariable String countryName1,
            @PathVariable String countryName2,
            @PathVariable String beanName,
            @PathVariable(name = "year") int yearValue
    ) {
        Optional<GDP> gdp1 = gdpRepository.findAllReferencesByYearYearAndCountryName(yearValue, countryName1);
        Optional<GDP> gdp2 = gdpRepository.findAllReferencesByYearYearAndCountryName(yearValue, countryName2);
        Optional<Beans> bean = beansRepository.findReferenceByName(beanName);

        if (!gdp1.isPresent()) throw new GDPinBeanNotFoundException(countryName1, yearValue);
        if (!gdp2.isPresent()) throw new GDPinBeanNotFoundException(countryName2, yearValue);
        if (!bean.isPresent()) throw new IllegalArgumentException("Bean not found: " + beanName);

        BigDecimal beanValue = bean.get().getBeanPrice();

        if (beanValue.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }

        BigDecimal gdpAmount1 = gdp1.get().getGdpAmount();
        BigDecimal gdpAmount2 = gdp2.get().getGdpAmount();

        BigDecimal ratioNumerator = gdpAmount2.divide(beanValue, 2, RoundingMode.HALF_UP);
        BigDecimal ratioDenominator = gdpAmount1.divide(beanValue, 2, RoundingMode.HALF_UP);

        BigDecimal gdpRatio = ratioNumerator.divide(ratioDenominator, 2, RoundingMode.HALF_UP);

        GDPRatioDto dto = new GDPRatioDto(gdpRatio.setScale(2, RoundingMode.HALF_UP));

        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
}