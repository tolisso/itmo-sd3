package ru.akirakozov.sd.refactoring.database;

import ru.akirakozov.sd.refactoring.dto.ProductDto;
import ru.akirakozov.sd.refactoring.exception.SqlException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final String connectionUrl;

    public ProductRepository(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void save(String name, int price) {
        String sql = "INSERT INTO PRODUCT " + "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";

        try (Connection c = DriverManager.getConnection(connectionUrl);
             Statement stmt = c.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SqlException("Exception while saving into PRODUCT", e);
        }
    }

    public List<ProductDto> findAll() {
        final String sql = "SELECT * FROM PRODUCT";

        try (Connection c = DriverManager.getConnection(connectionUrl);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            final List<ProductDto> result = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                result.add(new ProductDto(name, price));
            }
            return result;

        } catch (SQLException e) {
            throw new SqlException("Exception while finding all records", e);
        }
    }

    public ProductDto getMax() {
        return getRecord("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", "max");
    }

    public ProductDto getMin() {
        return getRecord("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", "min");
    }

    private ProductDto getRecord(String sql, String methodDescription) {
        try (Connection c = DriverManager.getConnection(connectionUrl);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return new ProductDto(rs.getString("name"), rs.getInt("price"));
            } else {
                throw new SqlException("Can't find " + methodDescription + " because no records in table PRODUCT");
            }
        } catch (SQLException e) {
            throw new SqlException("Exception while getting " + methodDescription + " record", e);
        }
    }

    private int getIntValue(String sql, String methodName) {
        try (Connection c = DriverManager.getConnection(connectionUrl);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SqlException("Empty response from" + methodName);
            }
        } catch (SQLException e) {
            throw new SqlException("Exception while computing " + methodName + " on records", e);
        }
    }

    public int count() {
        return getIntValue("SELECT COUNT(*) FROM PRODUCT", "count");
    }

    public int sum() {
        return getIntValue("SELECT SUM(price) FROM PRODUCT", "sum");
    }
}
