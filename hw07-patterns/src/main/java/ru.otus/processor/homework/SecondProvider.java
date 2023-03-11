package ru.otus.processor.homework;

import java.util.Calendar;

public class SecondProvider {

    public int provideSecond(){
        Calendar calendar =Calendar.getInstance();

        int second = calendar.get(Calendar.SECOND);

        return second;
    }

}
