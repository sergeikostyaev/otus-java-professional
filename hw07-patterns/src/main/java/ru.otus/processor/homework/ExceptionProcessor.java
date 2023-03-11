package ru.otus.processor.homework;

import lombok.AllArgsConstructor;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
@AllArgsConstructor
public class ExceptionProcessor implements Processor {
    private final SecondProvider secondProvider;

    @Override
    public Message process(Message message) {

        if(secondProvider.provideSecond() % 2 == 0){
            throw new RuntimeException("ExceptionProcessor throws exception");
        }

        return message;
    }

}

