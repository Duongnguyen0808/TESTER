package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ConfigReader {

    private static ConfigReader instance;

    private final Properties properties = new Properties();
    private final String activeEnv;

    private ConfigReader() {
        this.activeEnv = System.getProperty("env", "dev").trim();
        Path configPath = Path.of("src", "test", "resources", "config-" + activeEnv + ".properties");

        try (InputStream input = Files.newInputStream(configPath)) {
            properties.load(input);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot read config file: " + configPath, ex);
        }
    }

    public static synchronized ConfigReader getInstance() {
        String env = System.getProperty("env", "dev").trim();
        if (instance == null || !instance.activeEnv.equals(env)) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public int getRetryCount() {
        return Integer.parseInt(getRequired("retry.count"));
    }

    public String getEnv() {
        return activeEnv;
    }

    private String getRequired(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing config key: " + key + " in env=" + activeEnv);
        }
        return value.trim();
    }
}
