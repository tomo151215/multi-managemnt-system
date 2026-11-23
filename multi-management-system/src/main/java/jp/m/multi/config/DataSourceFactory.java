package jp.m.multi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DataSourceFactory {
    private DataSourceFactory() {}
    public static HikariDataSource create(AppConfig app) {
        HikariConfig config = new HikariConfig();
        config.setPoolName("school-db");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(app.getDbUrl());
        config.setUsername(app.getDbUser());
        config.setPassword(app.getDbPassword());
        config.setMaximumPoolSize(app.getPoolMaximum());
        config.setMinimumIdle(0);
        config.setConnectionTimeout(5000);
        config.setValidationTimeout(3000);
        config.setInitializationFailTimeout(-1);
        config.setConnectionInitSql("SET time_zone = '+09:00'");
        config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
        return new HikariDataSource(config);
    }
}
