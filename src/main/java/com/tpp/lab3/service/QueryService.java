package com.tpp.lab3.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

        // Метод для виконання DROP запиту
    public void executeDropCommand(String sqlQuery) throws SQLException {
        validateSqlQuery(sqlQuery, "drop");
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);
        stmt.executeUpdate();
}

        // Метод для виконання Insert запиту
    public void executeInsertCommand(String sqlQuery) throws SQLException {
        validateSqlQuery(sqlQuery, "insert");
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);
        stmt.executeUpdate();
    }

    // Метод для виконання Delete запиту
    public void executeDeleteCommand(String sqlQuery) throws SQLException {
        validateSqlQuery(sqlQuery, "delete");
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);
        stmt.executeUpdate();
    }

    // Метод для виконання Select запиту
    public List<List<String>> executeSelectCommand(String sqlQuery) throws SQLException {
        validateSqlQuery(sqlQuery, "select");
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);
        ResultSet rs = stmt.executeQuery();

        List<List<String>> result = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getString(i));
            }
            result.add(row);
        }

        return result;
    }

    // Загальна перевірка SQL-запитів
    private void validateSqlQuery(String sqlQuery, String expectedCommand) throws SQLException {
        String lowerQuery = sqlQuery.toLowerCase().trim();

        // Перевіряємо, чи починається запит з очікуваного типу команди
        if (!lowerQuery.startsWith(expectedCommand)) {
            throw new SQLException("Invalid command: Expected a " + expectedCommand + " query.");
        }

        // Забороняємо DROP, TRUNCATE, ALTER, UPDATE, UNION та інші небезпечні запити
        if (lowerQuery.contains("drop") || lowerQuery.contains("truncate") || lowerQuery.contains("alter") ||
            lowerQuery.contains("update") || lowerQuery.contains("union") || lowerQuery.contains("--") ||
            lowerQuery.contains(";") || lowerQuery.matches(".*1\\s*=\\s*1.*")) {
            throw new SQLException("Unsafe SQL query detected. This query is not allowed.");
        }
    }

}