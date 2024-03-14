package com.bbdgrad.thebeanindex.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GDPRatioDto {
    private BigDecimal ratio;

    public GDPRatioDto(BigDecimal ratio) {
        this.ratio = ratio;
    }

}
