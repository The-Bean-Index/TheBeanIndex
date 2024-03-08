package com.bbdgrad.thebeanindex;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "beans")
public class Beans {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public Beans(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
