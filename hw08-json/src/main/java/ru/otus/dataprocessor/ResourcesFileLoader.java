package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {

    private final Reader reader;

    public ResourcesFileLoader(String fileName) {
        reader = new InputStreamReader(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("inputData.json")));
    }

    @Override
    public List<Measurement> load() {

        var gson = new Gson();

        List<Measurement> list = Arrays.stream(gson.fromJson(reader, Measurement[].class)).toList();

        return list;
    }
}
