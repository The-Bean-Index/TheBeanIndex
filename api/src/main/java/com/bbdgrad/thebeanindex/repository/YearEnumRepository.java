package com.bbdgrad.thebeanindex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbdgrad.thebeanindex.model.YearEnum;

public interface YearEnumRepository extends JpaRepository<YearEnum, Integer>{
    
    Optional<YearEnum> findAllReferencesByYear(int year);

    Optional<YearEnum> findReferenceByYear(int year);
}
