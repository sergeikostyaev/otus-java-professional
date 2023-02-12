package ru.otus;

public class Main {
    public static void main(String[] args) {
        TestLoggingInterface tli = Ioc.createTestLogging();
        tli.calculation(1);
        tli.calculation(1,6);
        tli.calculation(1,"String");
        tli.calculation(5, "String", "String");
        tli.calculation("String");
        tli.calculation("String", "String");
    }
}