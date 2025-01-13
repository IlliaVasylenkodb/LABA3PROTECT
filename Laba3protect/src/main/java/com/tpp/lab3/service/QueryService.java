package com.tpp.lab3.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

    private final Connection connection;

    @Autowired
    public QueryService(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    // Метод для виконання SQL запиту (SELECT)
    public List<List<String>> executeSelectCommand(String sqlQuery) throws SQLException {
        // Перевірка на безпеку запиту
        checkForUnsafeSelect(sqlQuery);

        // Виконання запиту
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);
        ResultSet rs = stmt.executeQuery();
        
        // Список для збереження результатів
        List<List<String>> result = new ArrayList<>();
        
        // Отримуємо метадані результату запиту
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        // Проходимо по всіх рядках і витягуємо значення
        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getString(i));  // Додаємо значення кожного стовпця
            }
            result.add(row); // Додаємо рядок в результат
        }

        return result;
    }

    // Метод для виконання Insert запиту
    public void executeInsertCommand(String sqlQuery) throws SQLException {
        // Виконання запиту
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);
        stmt.executeUpdate();
    }

    // Метод для виконання Delete запиту
    public void executeDeleteCommand(String sqlQuery) throws SQLException {
        // Виконання запиту
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);
        stmt.executeUpdate();
    }

    // Перевірка на небезпечні SELECT запити
    private void checkForUnsafeSelect(String sqlQuery) throws SQLException {
        String lowerQuery = sqlQuery.toLowerCase().trim();

        // Дозволяємо запити типу SELECT * FROM <table>, без WHERE та інших небезпечних частин
        if (lowerQuery.startsWith("select * from") && !lowerQuery.contains("where") && !lowerQuery.contains("or") && !lowerQuery.contains("1=1")) {
            return;
        }

        // Блокуємо запити з WHERE, OR, 1=1, DROP, DELETE, TRUNCATE, UNION та інші
        if (lowerQuery.matches("(?i).*select\\s+\\*\\s+from\\s+.+\\s+where.*") ||
            lowerQuery.contains("or 1=1") ||
            lowerQuery.contains("union") ||
            lowerQuery.contains("drop") ||
            lowerQuery.contains("delete") ||
            lowerQuery.contains("truncate") ||
            lowerQuery.matches("(?i).*select\\s+.*from.*--.*")) {
            throw new SQLException("Unsafe SELECT query detected. This query is not allowed.");
        }
    }
}
