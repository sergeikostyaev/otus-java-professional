package ru.otus;

import lombok.Getter;

import java.util.InputMismatchException;

@Getter
public class Banknote {
   private final int value;

    public Banknote(int value) {
        if(value == 50 || value == 100 ||value == 500 ||value == 1000) {
            this.value = value;
        }else{
            throw new InputMismatchException("Wrong banknote value");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
