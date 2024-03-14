package com.bbdgrad.thebeanindex.dto;

import lombok.Data;

import java.util.List;

@Data
public class BeansResponseDto {
    private List<BeansDto> Beans;

    public BeansResponseDto(List<BeansDto> beans) {
        this.Beans = beans;
    }
}
