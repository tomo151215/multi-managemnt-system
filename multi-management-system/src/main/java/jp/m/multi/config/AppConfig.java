package jp.m.multi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private final boolean secureCookie;
    private final int sessionTimeout;
    private final int poolMaximum;

    public AppConfig(String dbUrl, String dbUser, String dbPassword,
            boolean secureCookie, int sessionTimeout, int poolMaximum) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.secureCookie = secureCookie;
        this.sessionTimeout = sessionTimeout;
        this.poolMaximum = poolMaximum;
    }

    public static AppConfig load() {
        Properties properties = new Properties();
        // try (...)を抜けるとInputStreamが自動で閉じられます。
        try (InputStream input = AppConfig.class.getResourceAsStream("/application.properties")) {
            if (input == null) {
                throw new IllegalStateException("application.properties is missing");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load application configuration");
        }
        String secure = System.getenv("APP_COOKIE_SECURE");
        if (secure == null) {
            secure = "true";
        }
        if (!secure.equals("true") && !secure.equals("false")) {
            throw new IllegalStateException("APP_COOKIE_SECURE must be true or false");
        }
        return new AppConfig(required("DB_URL"), required("DB_USER"), required("DB_PASSWORD"),
                Boolean.parseBoolean(secure),
                Integer.parseInt(properties.getProperty("session.timeout.seconds")),
                Integer.parseInt(properties.getProperty("db.pool.maximum")));
    }

    private static String required(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(name + " is required");
        }
        return value;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public boolean isSecureCookie() {
        return secureCookie;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public int getPoolMaximum() {
        return poolMaximum;
    }

    @Override
    public String toString() {
        return "AppConfig[credentials=REDACTED]";
    }
}