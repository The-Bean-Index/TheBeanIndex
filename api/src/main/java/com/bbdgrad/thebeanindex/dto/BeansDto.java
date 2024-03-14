package com.bbdgrad.thebeanindex.dto;

import com.bbdgrad.thebeanindex.model.Beans;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeansDto {
    
    private Integer id;

    private String name;

    private BigDecimal beanPrice;

    public static BeansDto toDto(Beans beans) {
        BeansDto dto = new BeansDto(); 
        dto.setId(beans.getId());
        dto.setName(beans.getName());
        dto.setBeanPrice(beans.getBeanPrice());

        return dto;
    }
}