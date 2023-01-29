package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
       runTest("ru.otus.Test");
    }





    private static void runTest(String s) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class<?> clazz = Class.forName(s);
        List<Method> before = getBeforeList(clazz.getDeclaredConstructor().newInstance());
        List<Method> after = getAfterList(clazz.getDeclaredConstructor().newInstance());
        List<Method> tests = getTestsList(clazz.getDeclaredConstructor().newInstance());
        int accepted = 0;
        int nonAccepted = 0;

        for (Method test : tests) {
            var invoker = clazz.getDeclaredConstructor().newInstance();
            runMethodList(before, invoker, null);

            try {
                test.invoke(invoker, null);
                accepted++;
            } catch (Exception e) {
                nonAccepted++;
            }
            runMethodList(after, invoker, null);

            System.out.println(" ");
            System.out.println("---------------------------------------------------------");
            System.out.println(" ");

        }
        System.out.println("Accepted: " + accepted + ", not accepted: " + nonAccepted);
    }
    private static<T> void runMethodList(List<Method> al, Object invoker, T parameter) throws InvocationTargetException, IllegalAccessException {
        if(parameter == null){
            for(Method method : al){
                method.invoke(invoker, null);
            }
        }else {
            for (Method method : al) {
                method.invoke(invoker, parameter);
            }
        }
    }

    private static <T> ArrayList<Method> getBeforeList(T clazz){
        Class<?> t = clazz.getClass();
        ArrayList<Method> al = new ArrayList<>();
        var field = t.getDeclaredMethods();
        for (Method method : field) {
            if (method.isAnnotationPresent(MyBeforeAnnotation.class)) {
                al.add(method);
            }
        }
        return al;
    }

    private static <T> ArrayList<Method> getAfterList(T clazz){
        Class<?> t = clazz.getClass();
        ArrayList<Method> al = new ArrayList<>();
        var field = t.getDeclaredMethods();
        for (Method method : field) {
            if (method.isAnnotationPresent(MyAfterAnnotation.class)) {
                al.add(method);
            }
        }
        return al;
    }
    private static <T> ArrayList<Method> getTestsList(T clazz){
        Class<?> t = clazz.getClass();
        ArrayList<Method> al = new ArrayList<>();
        var field = t.getDeclaredMethods();
        for (Method method : field) {
            if (method.isAnnotationPresent(MyTestAnnotation.class)) {
                al.add(method);
            }
        }
        return al;
    }
}