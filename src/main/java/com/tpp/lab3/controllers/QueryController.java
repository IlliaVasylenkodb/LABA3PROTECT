package com.tpp.lab3.controllers;

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

    // Отримання сторінки з формою вводу запиту
    @GetMapping
    public String queryForm() {
        return "query"; // Повертає шаблон query.html
    }

    // Обробка SQL запиту
    @PostMapping("/execute")
    public String executeQuery(@RequestParam("sqlQuery") String sqlQuery, Model model) {
        try {
            // Перевіряємо тип команди
            String commandType = sqlQuery.trim().split(" ")[0].toLowerCase();
            
            // Обробка команди insert
            if (commandType.equals("insert")) {
                queryService.executeInsertCommand(sqlQuery);
                model.addAttribute("message", "Insert command executed successfully!");
            }
            // Обробка команди delete
            else if (commandType.equals("delete")) {
                queryService.executeDeleteCommand(sqlQuery);
                model.addAttribute("message", "Delete command executed successfully!");
            }
            // Обробка команди read (select)
            else if (commandType.equals("select")) {
                var result = queryService.executeSelectCommand(sqlQuery);
                model.addAttribute("queryResult", result);  // Передаём результаты на страницу
            } else {
                model.addAttribute("error", "Invalid command type.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error executing command: " + e.getMessage());
        }

        return "query"; // Повертаємо на сторінку з результатами
    }
}
