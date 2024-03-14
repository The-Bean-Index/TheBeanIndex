package com.bbdgrad.thebeanindex.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbdgrad.thebeanindex.model.GDP;
import com.bbdgrad.thebeanindex.dto.YearEmunDto;
import com.bbdgrad.thebeanindex.dto.YearEnumAllowedYearsDto;
import com.bbdgrad.thebeanindex.dto.YearsEnumDto;
import com.bbdgrad.thebeanindex.model.Beans;
import com.bbdgrad.thebeanindex.model.YearEnum;
import com.bbdgrad.thebeanindex.repository.YearEnumRepository;
import com.bbdgrad.thebeanindex.repository.BeansRepository;

@RestController
@RequestMapping("/year")
public class YearEnumController {

    @Autowired
    YearEnumRepository yearEnum;

    @Autowired
    BeansRepository beansRepository;
    
    @GetMapping("/{year}/{beanName}")
    public ResponseEntity<YearEmunDto> getCountriesByYear(@PathVariable int year, @PathVariable String beanName) {
        Optional<YearEnum> response = yearEnum.findAllReferencesByYear(year);
        Optional<Beans> bean = beansRepository.findReferenceByName(beanName);

        
        if (response.isEmpty() || !bean.isPresent() || bean.get().getBeanPrice().compareTo(BigDecimal.ZERO) == 0) {
            return new ResponseEntity<>(new YearEmunDto(), HttpStatus.NOT_FOUND);
        } 

        BigDecimal beanValue = bean.get().getBeanPrice();

        List<GDP> gdps = response.get().getGdps().stream()
            .map((gdp) -> {
                gdp.setGdpAmount(gdp.getGdpAmount().divide(beanValue, 2, RoundingMode.HALF_UP));

                return gdp;
            })
            .toList();
        
        response.get().setGdps(gdps);
        

        YearEmunDto dto = YearEmunDto.toDto(response.get());

        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @GetMapping("country/{country}/{beanName}")
    public ResponseEntity<YearsEnumDto> getCountryGdpForLast5Years(@PathVariable String country, @PathVariable String beanName) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");

        int year = Integer.parseInt(format.format(date));

        List<YearEmunDto> dtos = IntStream.range(year - 4, year + 1)
            .mapToObj((y) -> {
                YearEmunDto dto = getCountriesByYear(y, beanName).getBody();
                dto.setYear(dto.getYear() == null ? y : dto.getYear());

                return dto;
            })
            .map((dto) -> {
                if (dto.getId() == null)  return dto;
                
                dto.setGdps(dto.getGdps().stream()
                    .filter((gdpCountry) -> gdpCountry.getCountry().getName().equalsIgnoreCase(country))
                    .toList());
                return dto;
            })
            .toList();

        return new ResponseEntity<>(YearsEnumDto.toDto(dtos), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public YearEnumAllowedYearsDto getAllYears() {
        List<YearEnum> yearEnums = yearEnum.findAll();
        List<Integer> years = yearEnums.stream()
                                       .map(YearEnum::getYear)
                                       .collect(Collectors.toList());

        return new YearEnumAllowedYearsDto(years);
    }
}
