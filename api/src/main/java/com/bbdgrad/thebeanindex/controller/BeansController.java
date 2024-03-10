package com.bbdgrad.thebeanindex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bbdgrad.thebeanindex.repository.BeansRepository;
import com.bbdgrad.thebeanindex.Dtos.BeansDto;
import com.bbdgrad.thebeanindex.model.Beans;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/beans")
public class BeansController {
    
    BeansRepository beansRepository;

    @Autowired
    public BeansController(BeansRepository beansRepository) {
        this.beansRepository = beansRepository;
    }

    @GetMapping("/")
    public List<BeansDto> getAllBeanNames() {
        List<Beans> beans = beansRepository.findAll();
        return beans.stream().map(BeansDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllBeanPrices() {
        List<String> beanNames = beansRepository.findAll().stream()
                .map(Beans::getName)
                .map(Object::toString)  
                .collect(Collectors.toList());
        
        return new ResponseEntity<>(beanNames, HttpStatus.OK);
    }
}