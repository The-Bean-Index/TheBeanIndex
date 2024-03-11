package com.bbdgrad.thebeanindex.repository;

import com.bbdgrad.thebeanindex.model.Beans;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeansRepository extends JpaRepository<Beans, Integer> {

    Optional<Beans> findReferenceByName(String name);
}

