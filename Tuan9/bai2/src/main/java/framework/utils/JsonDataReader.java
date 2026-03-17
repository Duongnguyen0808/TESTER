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

    public static List<Map<String, String>> readRows(String relativePathFromResources) {
        Path jsonPath = Path.of("src", "test", "resources").resolve(relativePathFromResources);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonPath.toFile(), new TypeReference<List<Map<String, String>>>() {
            });
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot read JSON: " + jsonPath, ex);
        }
    }

    public static Object[][] toMatrix(List<Map<String, String>> rows, String... keys) {
        Object[][] data = new Object[rows.size()][keys.length];
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < keys.length; j++) {
                data[i][j] = rows.get(i).getOrDefault(keys[j], "");
            }
        }
        return data;
    }
}
