package com.bbdgrad.thebeanindex.dto;

import lombok.Data;

import java.util.List;

@Data
public class BeanNamesDto {
    private List<String> beanNames;

    public BeanNamesDto(List<String> beanNames) {
        this.beanNames = beanNames;
    }
}
