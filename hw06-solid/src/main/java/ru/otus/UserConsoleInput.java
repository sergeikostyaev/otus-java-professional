package ru.otus;

import java.util.Scanner;

public class UserConsoleInput implements InputService<Integer>{
    @Override
    public Integer read() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
