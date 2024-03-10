package com.bbdgrad.thebeanindex.Dtos;

import java.util.List;

import com.bbdgrad.thebeanindex.model.YearEnum;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YearEmunDto {

    private Integer id;

    private Integer year;

    private List<GDPDto> gdps;

    public static YearEmunDto toDto(YearEnum year) {
        YearEmunDto dto = new YearEmunDto();
        dto.setId(year.getId());
        dto.setYear(year.getYear());
        dto.setGdps(year.getGdps().stream()
            .map((model) -> GDPDto.toDto(model))
            .toList());

        return dto;
    }
}
