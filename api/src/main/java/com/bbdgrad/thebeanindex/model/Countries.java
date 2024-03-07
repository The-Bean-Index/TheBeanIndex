package com.bbdgrad.thebeanindex;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// @NoArgsConstructor
@Entity
@Table(name = "countries")
public class Countries {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public Countries(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}