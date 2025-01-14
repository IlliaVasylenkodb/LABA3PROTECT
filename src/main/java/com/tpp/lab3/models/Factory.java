package com.tpp.lab3.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "factory")
public class Factory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factory_id")
    private Integer factoryId;

    @NotEmpty(message = "Factory name cannot be empty")
    @Size(max = 100, message = "Factory name must not exceed 100 characters")
    @Column(name = "name")
    private String name;


    @Column(name = "country_id")
    private Integer countryId;

    public Factory() {
    }

    public Factory(Integer factoryId, String name, String computerName, Integer countryId) {
        this.factoryId = factoryId;
        this.name = name;
        this.countryId = countryId;
    }

    // Геттеры и сеттеры
    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
}
