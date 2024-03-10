package com.bbdgrad.thebeanindex.Dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YearsEnumDto {
    
    private List<YearEmunDto> years;

    public static YearsEnumDto toDto(List<YearEmunDto> dtos) {
        YearsEnumDto yearDtos = new YearsEnumDto();
        yearDtos.setYears(dtos);
    
        return yearDtos;
    }
}
