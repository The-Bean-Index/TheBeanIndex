package com.bbdgrad.thebeanindex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbdgrad.thebeanindex.model.GDPwithYear;

public interface GDPwithYearRepository extends JpaRepository<GDPwithYear, Integer>{
    
    Optional<GDPwithYear> findAllReferencesByYearYearAndCountryName(int year, String country);
}
