package com.seguridad.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final HikariDataSource dataSource;

    static {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("jTDS JDBC Driver not found in classpath", e);
        }
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
        config.setJdbcUrl("jdbc:jtds:sqlserver://./seguridad_db;namedPipe=true;instance=SQLEXPRESS;user=app_user;password=AppUser2026#");
        config.setConnectionTestQuery("SELECT 1");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
