package com.bbdgrad.thebeanindex.dto;

import lombok.Data;

import java.util.List;

@Data
public class YearEnumAllowedYearsDto {

    private List<Integer> years;

    public YearEnumAllowedYearsDto(List<Integer> years) {
        this.years = years;
    }
}

