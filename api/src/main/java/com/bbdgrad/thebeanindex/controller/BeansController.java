package com.bbdgrad.thebeanindex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbdgrad.thebeanindex.repository.BeansRepository;
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

    @GetMapping("/names")
    public List<String> getAllBeanNames() {
        List<Beans> beans = beansRepository.findAll();

        return beans.stream()
                .map(Beans::getName)
                .collect(Collectors.toList());
    }
}