package ru.otus;

public class Test {

    @MyBeforeAnnotation
    public void Before(){
        System.out.println("Running test, hash - " + hashCode());
    }
    @MyAfterAnnotation
    public void After(){
        System.out.println("Method was tested with " + hashCode() + " class");
    }

    @MyTestAnnotation
    public String Test1(){
        System.out.println("Test with exception, hash - " + hashCode());
        throw new RuntimeException();
    }
    @MyTestAnnotation
    public String Test2(){
        System.out.println("Test with exception, hash - " + hashCode());
        throw new RuntimeException();
    }
    @MyTestAnnotation
    public String Test3(){
        System.out.println("Test with no exception, hash - " + hashCode());
        return "Passed";
    }


















}
