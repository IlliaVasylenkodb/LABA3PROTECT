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

import com.tpp.lab3.models.Computer;
import com.tpp.lab3.models.Country;
import com.tpp.lab3.service.ComputerService;
import com.tpp.lab3.service.CountryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/computers")
public class ComputerController {
    @Autowired
    private ComputerService computerService;
    @Autowired
private CountryService countryService;

    @GetMapping
    public String listComputers(Model model) {
        model.addAttribute("computers", computerService.getAllComputers());
        return "computers";
    }

    @GetMapping("/add")
public String addComputerForm(Model model) {
    List<Country> countries = countryService.getAllCountries(); // Получаем все страны
    model.addAttribute("computer", new Computer()); // Новый объект для компьютера
    model.addAttribute("countries", countries);    // Добавляем список стран в модель
    return "add-computer"; // Страница добавления компьютера
}

    // Обработчик POST-запроса для добавления компьютера
    @PostMapping("/add")
    public String addComputer(@Valid @ModelAttribute("computer") Computer computer, BindingResult result) {
        if (result.hasErrors()) {
            return "add-computer";  // Если есть ошибки, вернем форму обратно
        }
        computerService.saveComputer(computer); // Сохраняем компьютер
        return "redirect:/computers"; // Перенаправляем на страницу со списком компьютеров
    }

    @GetMapping("/edit/{id}")
    public String editComputerForm(@PathVariable("id") Integer id, Model model) {
        Optional<Computer> computerOpt = computerService.findComputerById(id);
        if (computerOpt.isPresent()) {
            model.addAttribute("computer", computerOpt.get());
            return "edit-computer";
        }
        return "redirect:/computers";
    }

    @PostMapping("/update/{id}")
    public String updateComputer(@PathVariable("id") Integer id, @ModelAttribute("computer") Computer computer) {
        computer.setComputerId(id);
        computerService.updateComputer(computer);
        return "redirect:/computers";
    }

    @GetMapping("/delete/{id}")
    public String deleteComputer(@PathVariable("id") Integer id) {
        computerService.deleteComputerById(id);
        return "redirect:/computers";
    }
}
