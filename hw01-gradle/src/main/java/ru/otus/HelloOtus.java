package ru.otus;

import com.google.common.collect.ImmutableSet;

public class HelloOtus {
    public static void main(String... args) {
        ImmutableSet<String> is = ImmutableSet.of("A","B","C");
        is.forEach(System.out::println);
    }
}
