package com.bbdgrad.thebeanindex.model;


import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Yearenum")
public class Year {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer year;

    public Year(Integer id, Integer year) {
        this.Id = id;
        this.year = year;
    }
}

