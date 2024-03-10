package com.bbdgrad.thebeanindex.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbdgrad.thebeanindex.Dtos.YearEmunDto;
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
}
