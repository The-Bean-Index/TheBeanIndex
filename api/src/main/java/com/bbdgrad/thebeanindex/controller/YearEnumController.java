package com.bbdgrad.thebeanindex.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbdgrad.thebeanindex.Dtos.YearEmunDto;
import com.bbdgrad.thebeanindex.Dtos.YearsEnumDto;
import com.bbdgrad.thebeanindex.model.YearEnum;
import com.bbdgrad.thebeanindex.repository.YearEnumRepository;

@RestController
@RequestMapping("/year")
public class YearEnumController {

    @Autowired
    YearEnumRepository yearEnum;
    
    @GetMapping("/{year}")
    public ResponseEntity<YearEmunDto> getCountriesByYear(@PathVariable int year) {
        Optional<YearEnum> response = yearEnum.findAllReferencesByYear(year);

        if (response.isEmpty()) {
            return new ResponseEntity<>(new YearEmunDto(), HttpStatus.NOT_FOUND);
        } 

        YearEmunDto dto = YearEmunDto.toDto(response.get());

        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @GetMapping("country/{country}")
    public ResponseEntity<?> getCountryGdpForLast5Years(@PathVariable String country) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");

        int year = Integer.parseInt(format.format(date));

        List<YearEmunDto> dtos = IntStream.range(year - 5, year + 1)
            .mapToObj((y) -> {
                YearEmunDto dto = getCountriesByYear(y).getBody();
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
}
