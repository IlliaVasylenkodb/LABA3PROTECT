package com.tpp.lab3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpp.lab3.models.Country;
import com.tpp.lab3.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> findCountryById(Integer id) {
        return countryRepository.findById(id);
    }

    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    public void updateCountry(Country updatedCountry) {
        Optional<Country> existingCountryOpt = countryRepository.findById(updatedCountry.getCountryId());

        if (existingCountryOpt.isPresent()) {
            Country existingCountry = existingCountryOpt.get();
            existingCountry.setName(updatedCountry.getName());
            countryRepository.save(existingCountry);
        }
    }

    public void deleteCountryById(Integer id) {
        countryRepository.deleteById(id);
    }
}
