package com.tpp.lab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpp.lab3.models.Factory;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Integer> {

}
