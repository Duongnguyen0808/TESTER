package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties = new Properties();

    public ConfigReader(String env) {
        String activeEnv = (env == null || env.isBlank()) ? "dev" : env.trim();
        Path configPath = Path.of("src", "test", "resources", "config-" + activeEnv + ".properties");
        try (InputStream input = Files.newInputStream(configPath)) {
            properties.load(input);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot read config file: " + configPath, ex);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing config key: " + key);
        }
        return value.trim();
    }
}
