package com.bbdgrad.thebeanindex.Dtos;

import java.util.List;

public class BeansResponseDto {
    private List<BeansDto> Beans;

    public BeansResponseDto(List<BeansDto> beans) {
        this.Beans = beans;
    }

    public List<BeansDto> getBeans() {
        return Beans;
    }

    public void setBeans(List<BeansDto> beans) {
        this.Beans = beans;
    }
}
