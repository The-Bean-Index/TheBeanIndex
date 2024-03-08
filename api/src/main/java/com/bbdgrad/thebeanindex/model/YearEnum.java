package com.bbdgrad.thebeanindex.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "YearEnum")
public class YearEnum {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer year;

    public YearEnum(Integer id, Integer year) {
        this.id = id;
        this.year = year;
    }
}
