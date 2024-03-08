package com.bbdgrad.thebeanindex.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GDP")
public class GDP {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "year_id")
    private Integer yearId;

    @Column(name = "gdp_amount", precision = 10, scale = 2)
    private BigDecimal gdpAmount;

    public GDP(Integer id, Integer countryId, Integer yearId, BigDecimal gdpAmount) {
        this.id = id;
        this.countryId = countryId;
        this.yearId = yearId;
        this.gdpAmount = gdpAmount;
    }
}
