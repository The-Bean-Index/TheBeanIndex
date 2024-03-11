package com.bbdgrad.thebeanindex.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class YearEnumAllowedYearsDto {

    private List<Integer> years;

    public YearEnumAllowedYearsDto(List<Integer> years) {
        this.years = years;
    }

    // Getters and setters (optional, can be generated using Lombok)
}

