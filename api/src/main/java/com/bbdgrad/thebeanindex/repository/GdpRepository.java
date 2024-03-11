package com.bbdgrad.thebeanindex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbdgrad.thebeanindex.model.GDP;

public interface GdpRepository extends JpaRepository<GDP, Integer>{
    
    Optional<GDP> findAllReferencesByYearYearAndCountryName(int year, String country);
}
