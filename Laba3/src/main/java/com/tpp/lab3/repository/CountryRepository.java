package com.tpp.lab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpp.lab3.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}

