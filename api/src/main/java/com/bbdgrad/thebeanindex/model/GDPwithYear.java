package com.bbdgrad.thebeanindex.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "GDP")
public class GDPwithYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "country_id")
    private Countries country;

    @ManyToOne()
    @JoinColumn(name = "year_id")
    private Year year;

    @Column(name = "gdp_amount", precision = 10, scale = 2)
    private BigDecimal gdpAmount;

    public GDPwithYear(Integer id, Countries country, Year year, BigDecimal gdpAmount) {
        this.id = id;
        this.country = country;
        this.year = year;
        this.gdpAmount = gdpAmount;
    }
}
