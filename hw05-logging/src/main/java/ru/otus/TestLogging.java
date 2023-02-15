package ru.otus;

public class TestLogging implements TestLoggingInterface{
    TestLogging(){

    }
    @Log
    @Override
    public void calculation(int a) {

    }

    @Override
    public void calculation(int a, int b) {

    }

    @Log
    @Override
    public void calculation(int a, String b) {

    }

    @Override
    public void calculation(int a, String b, String c) {

    }

    @Log
    @Override
    public void calculation(String a) {

    }

    @Log
    @Override
    public void calculation(String a, String b) {

    }

    @Log
    @Override
    public void calculation(String a, int b) {

    }

}
