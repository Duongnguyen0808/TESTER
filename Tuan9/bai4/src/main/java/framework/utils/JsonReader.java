package framework.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import framework.model.UserData;

public final class JsonReader {

    private JsonReader() {
    }

    public static List<UserData> readUsers(String relativePathFromResources) {
        Path file = Path.of("src", "test", "resources").resolve(relativePathFromResources);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file.toFile(), new TypeReference<List<UserData>>() {
            });
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot read users json: " + file, ex);
        }
    }
}
