package com.tpp.lab3.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tpp.lab3.models.Country;
import com.tpp.lab3.service.CountryService;

@Controller
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public String listCountries(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        return "countries";
    }

    @GetMapping("/add")
    public String addCountryForm(Model model) {
        model.addAttribute("country", new Country());
        return "add-country";
    }

    @PostMapping("/add")
    public String addCountry(@ModelAttribute("country") Country country) {
        countryService.saveCountry(country);
        return "redirect:/countries";
    }

    @GetMapping("/edit/{id}")
    public String editCountryForm(@PathVariable("id") Integer id, Model model) {
        Optional<Country> countryOpt = countryService.findCountryById(id);
        if (countryOpt.isPresent()) {
            model.addAttribute("country", countryOpt.get());
            return "edit-country";
        }
        return "redirect:/countries";
    }

    @PostMapping("/update/{id}")
    public String updateCountry(@PathVariable("id") Integer id, @ModelAttribute("country") Country country) {
        country.setCountryId(id);
        countryService.updateCountry(country);
        return "redirect:/countries";
    }

    @GetMapping("/delete/{id}")
    public String deleteCountry(@PathVariable("id") Integer id) {
        countryService.deleteCountryById(id);
        return "redirect:/countries";
    }
}
