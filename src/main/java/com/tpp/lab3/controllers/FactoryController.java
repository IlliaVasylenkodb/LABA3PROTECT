package com.tpp.lab3.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tpp.lab3.models.Country;
import com.tpp.lab3.models.Factory;
import com.tpp.lab3.service.CountryService;
import com.tpp.lab3.service.FactoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/factories")
public class FactoryController {

    @Autowired
    private FactoryService factoryService;
    @Autowired
private CountryService countryService;

    @GetMapping
    public String listFactories(Model model) {
        model.addAttribute("factories", factoryService.getAllFactories());
        return "factories";
    }

    @GetMapping("/add")
    public String addFactoryForm(Model model) {
        // Получаем все страны из сервиса
        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("factory", new Factory()); // Новый объект для фабрики
        model.addAttribute("countries", countries);   // Список стран для выпадающего списка
        return "add-factory"; // Возвращаем страницу с формой добавления
    }

    // Обработчик POST-запроса для добавления фабрики
    @PostMapping("/add")
    public String addFactory(@Valid @ModelAttribute("factory") Factory factory, BindingResult result) {
        if (result.hasErrors()) {
            return "add-factory"; // Если есть ошибки, возвращаем на страницу добавления
        }
        factoryService.saveFactory(factory); // Сохраняем фабрику
        return "redirect:/factories"; // Перенаправляем на страницу со списком фабрик
    }

    @GetMapping("/edit/{id}")
    public String editFactoryForm(@PathVariable("id") Integer id, Model model) {
        Optional<Factory> factoryOpt = factoryService.findFactoryById(id);
        if (factoryOpt.isPresent()) {
            model.addAttribute("factory", factoryOpt.get());
            return "edit-factory";
        }
        return "redirect:/factories";
    }

    @PostMapping("/update/{id}")
    public String updateFactory(@PathVariable("id") Integer id, @Valid @ModelAttribute("factory") Factory factory,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "edit-factory";
        }
        factory.setFactoryId(id);
        factoryService.updateFactory(factory);
        return "redirect:/factories";
    }

    @GetMapping("/delete/{id}")
    public String deleteFactory(@PathVariable("id") Integer id) {
        factoryService.deleteFactoryById(id);
        return "redirect:/factories";
    }
}
