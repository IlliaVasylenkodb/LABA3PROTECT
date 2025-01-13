package com.tpp.lab3.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tpp.lab3.service.QueryService;


@Controller
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping
    public String queryForm() {
        return "query";
    }

    @PostMapping("/execute")
    public String executeQuery(@RequestParam("sqlQuery") String sqlQuery, Model model) {
        try {
            // Виконати запит через сервіс
            Map<String, Object> queryResult = queryService.executeQuery(sqlQuery);
            model.addAttribute("queryResult", queryResult);
        } catch (Exception e) {
            // Якщо сталася помилка
            model.addAttribute("error", "Error executing query: " + e.getMessage());
        }
        return "query";
    }
}
