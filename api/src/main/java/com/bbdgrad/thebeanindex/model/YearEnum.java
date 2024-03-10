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
public class YearEnum {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer year;

    @OneToMany(mappedBy = "yearId")
    List<GDP> gdps;

    public YearEnum(Integer id, Integer year, List<GDP> gdps) {
        this.id = id;
        this.year = year;
        this.gdps = gdps;
    }
}
