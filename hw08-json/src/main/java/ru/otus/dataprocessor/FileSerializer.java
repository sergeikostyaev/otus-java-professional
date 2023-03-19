package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class FileSerializer implements Serializer {

    private final String name;

    public FileSerializer(String fileName) {
        this.name = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        ObjectMapper om = new ObjectMapper();

        try {
            om.writeValue(new File(name), data);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
