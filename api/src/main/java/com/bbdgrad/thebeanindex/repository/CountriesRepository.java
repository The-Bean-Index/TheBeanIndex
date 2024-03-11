package com.bbdgrad.thebeanindex.repository;

import com.bbdgrad.thebeanindex.model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountriesRepository extends JpaRepository<Countries, Integer> {
    
    Optional<Countries> findReferenceByName(String name);
}
