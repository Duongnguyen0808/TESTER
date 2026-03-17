package framework.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonDataReader {

    private JsonDataReader() {
    }

    public static Object[][] readLoginData(String relativePathFromResources) {
        Path jsonPath = Path.of("src", "test", "resources").resolve(relativePathFromResources);
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Map<String, String>> rows = mapper.readValue(
                jsonPath.toFile(),
                new TypeReference<List<Map<String, String>>>() {
                }
            );

            Object[][] data = new Object[rows.size()][3];
            for (int i = 0; i < rows.size(); i++) {
                Map<String, String> row = rows.get(i);
                data[i][0] = row.getOrDefault("username", "");
                data[i][1] = row.getOrDefault("password", "");
                data[i][2] = row.getOrDefault("expectedResult", "");
            }
            return data;
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot read JSON file: " + jsonPath, ex);
        }
    }
}
