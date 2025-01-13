package com.tpp.lab3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

    private final JdbcTemplate jdbcTemplate;

    public QueryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> executeQuery(String sqlQuery) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery);
        List<String> columns = new ArrayList<>();
        List<List<Object>> resultRows = new ArrayList<>();

        if (!rows.isEmpty()) {
            // Отримати імена колонок
            columns.addAll(rows.get(0).keySet());

            // Отримати значення рядків
            for (Map<String, Object> row : rows) {
                resultRows.add(new ArrayList<>(row.values()));
            }
        }

        return Map.of(
            "columns", columns,
            "rows", resultRows
        );
    }
}
