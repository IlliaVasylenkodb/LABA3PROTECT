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
@Table(name = "computer")
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "computer_id")
    private Integer computerId;

    @NotEmpty(message = "Computer name cannot be empty")
    @Size(max = 100, message = "Computer name must not exceed 100 characters")
    @Column(name = "name")
    private String name;

    @Column(name = "factory_id")
    private Integer factoryId;


    @NotEmpty(message = "Computer name cannot be empty")
    @Size(max = 100, message = "Computer name must not exceed 100 characters")
    @Column(name = "Country_name")
    private String countryName; 
    public Computer() {
    }

    public Computer(Integer computerId, String name, Integer factoryId, String countryName) {
        this.computerId = computerId;
        this.name = name;
        this.factoryId = factoryId;
        this.countryName = countryName;
    }

    public Integer getComputerId() {
        return computerId;
    }

    public void setComputerId(Integer computerId) {
        this.computerId = computerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
