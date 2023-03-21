package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class  ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {

        Map<String,Double> map = new TreeMap<>(String::compareTo);

        data.stream().forEach(element -> {
            if(map.containsKey(element.getName())){
                map.put(element.getName(),map.get(element.getName())+element.getValue());
            }else{
                map.put(element.getName(), element.getValue());
            }
        });

        return map;
    }
}
