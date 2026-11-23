package jp.m.multi.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AppConfig {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private final boolean secureCookie;
    private final int sessionTimeout;
    private final int poolMaximum;

    public AppConfig(
            String dbUrl,
            String dbUser,
            String dbPassword,
            boolean secureCookie,
            int sessionTimeout,
            int poolMaximum) {

        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.secureCookie = secureCookie;
        this.sessionTimeout = sessionTimeout;
        this.poolMaximum = poolMaximum;
    }

    /**
     * 設定を読み込み、AppConfigを作る。
     */
    public static AppConfig load() {

        // 1. 全員共通の設定を読み込む
        Properties common = loadCommonProperties();

        // 2. 指定されていればローカル設定を読み込む
        Properties local = loadLocalProperties();

        // 3. DB設定を取得する
        // 環境変数があれば、ローカル設定より優先する
        String dbUrl = getRequiredSetting(
                local, "DB_URL", "db.url");

        String dbUser = getRequiredSetting(
                local, "DB_USER", "db.user");

        String dbPassword = getRequiredSetting(
                local, "DB_PASSWORD", "db.password");

        // 4. Cookieの設定を取得する
        String secureValue = getSetting(
                local, "APP_COOKIE_SECURE", "app.cookie.secure");

        // 指定がない場合はHTTPS向けの設定にする
        if (secureValue == null) {
            secureValue = "true";
        }

        if (!secureValue.equals("true")
                && !secureValue.equals("false")) {

            throw new IllegalStateException(
                    "APP_COOKIE_SECURE / app.cookie.secure "
                            + "must be true or false");
        }

        boolean secureCookie = Boolean.parseBoolean(secureValue);

        // 5. 共通設定の数値を取得する
        int sessionTimeout = getPositiveInt(
                common, "session.timeout.seconds");

        int poolMaximum = getPositiveInt(
                common, "db.pool.maximum");

        // 6. 読み取った値をまとめて返す
        return new AppConfig(
                dbUrl,
                dbUser,
                dbPassword,
                secureCookie,
                sessionTimeout,
                poolMaximum);
    }

    /**
     * src/main/resources/application.propertiesを読み込む。
     */
    private static Properties loadCommonProperties() {

        Properties properties = new Properties();

        InputStream input = AppConfig.class.getResourceAsStream(
                "/application.properties");

        if (input == null) {
            throw new IllegalStateException(
                    "application.properties was not found");
        }

        // Readerを使い、UTF-8で読み込む
        try (Reader reader = new InputStreamReader(
                input, StandardCharsets.UTF_8)) {

            properties.load(reader);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not read application.properties");
        }

        return properties;
    }

    /**
     * app.local.configで指定されたローカル設定を読み込む。
     */
    private static Properties loadLocalProperties() {

        Properties properties = new Properties();

        String filePath = System.getProperty("app.local.config");

        // 指定がなければローカルファイルは使わない
        if (filePath == null) {
            return properties;
        }

        if (filePath.isBlank()) {
            throw new IllegalStateException(
                    "app.local.config must not be empty");
        }

        Path path = Path.of(filePath);

        // 起動場所によって読み込み先が変わらないようにする
        if (!path.isAbsolute()) {
            throw new IllegalStateException(
                    "app.local.config must be an absolute path");
        }

        // 指定されたファイルをUTF-8で読み込む
        try (Reader reader = Files.newBufferedReader(
                path, StandardCharsets.UTF_8)) {

            properties.load(reader);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not read the local configuration file");
        }

        return properties;
    }

    /**
     * 環境変数を優先し、未設定ならローカル設定を使う。
     */
    private static String getSetting(
            Properties local,
            String environmentName,
            String propertyName) {

        String value = System.getenv(environmentName);

        if (value != null) {
            return value;
        }

        return local.getProperty(propertyName);
    }

    /**
     * 必須設定を取得する。
     */
    private static String getRequiredSetting(
            Properties local,
            String environmentName,
            String propertyName) {

        String value = getSetting(
                local, environmentName, propertyName);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    environmentName + " / "
                            + propertyName + " is required");
        }

        // パスワードなどの値は勝手にtrimしない
        return value;
    }

    /**
     * 共通設定から正の整数を取得する。
     */
    private static int getPositiveInt(
            Properties properties,
            String name) {

        String value = properties.getProperty(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    name + " is required");
        }

        int number;

        try {
            number = Integer.parseInt(value.trim());

        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    name + " must be an integer");
        }

        if (number <= 0) {
            throw new IllegalStateException(
                    name + " must be greater than zero");
        }

        return number;
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