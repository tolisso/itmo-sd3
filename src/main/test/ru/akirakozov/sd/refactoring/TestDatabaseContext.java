package ru.akirakozov.sd.refactoring;

import java.sql.*;

public class TestDatabaseContext {

    private final String testConnectionUrl;

    public TestDatabaseContext(String testConnectionUrl) {
        this.testConnectionUrl = testConnectionUrl;
    }

    public void executeSql(String sql) {
        try (Connection c = DriverManager.getConnection(testConnectionUrl)) {
            Statement stmt = c.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void reinitDatabase() {
        executeSql("DROP TABLE IF EXISTS PRODUCT");
        executeSql("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }
}
