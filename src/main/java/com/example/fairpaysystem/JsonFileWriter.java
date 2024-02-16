package com.example.fairpaysystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;

public class JsonFileWriter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeJsonToFile(String filePath, Object data) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing
        objectMapper.writeValue(new File(filePath), data);
    }
}