package com.bbdgrad.thebeanindex.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "beans")
public class Beans {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "beanprice", precision = 10, scale = 2)
    private BigDecimal beanPrice;

    public Beans(Integer id, String name, BigDecimal i) {
        this.id = id;
        this.name = name;
        this.beanPrice = i;
    }
}
