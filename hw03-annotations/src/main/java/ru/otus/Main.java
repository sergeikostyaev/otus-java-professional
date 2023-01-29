package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Main m = new Main();
        m.runTest("ru.otus.Test");
    }
    private void runTest(String s) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        Main m = new Main();
        Class<?> clazz = Class.forName(s);

        List<Method> before = m.getMethodList(clazz.getDeclaredConstructor().newInstance(),MyBeforeAnnotation.class);
        List<Method> after = m.getMethodList(clazz.getDeclaredConstructor().newInstance(),MyAfterAnnotation.class);
        List<Method> tests = m.getMethodList(clazz.getDeclaredConstructor().newInstance(),MyTestAnnotation.class);
        int accepted = 0;
        int nonAccepted = 0;

        for (Method test : tests) {
            var invoker = clazz.getDeclaredConstructor().newInstance();

            m.runMethodList(before, invoker, null);

            try {
                test.invoke(invoker, null);
                accepted++;
            } catch (Exception e) {
                nonAccepted++;
            }

            m.runMethodList(after, invoker, null);

            System.out.println(" ");
            System.out.println("---------------------------------------------------------");
            System.out.println(" ");

        }
        System.out.println("Accepted: " + accepted + ", not accepted: " + nonAccepted + ", total: " + tests.size());
    }

    private <T> void runMethodList(List<Method> al, Object invoker, T parameter) throws InvocationTargetException, IllegalAccessException {
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

    private <T> ArrayList<Method> getMethodList(T clazz, Class<? extends Annotation> clazz2){
        Class<?> t = clazz.getClass();
        ArrayList<Method> al = new ArrayList<>();
        var field = t.getDeclaredMethods();
        for (Method method : field) {
            if (method.isAnnotationPresent(clazz2)) {
                al.add(method);
            }
        }
        return al;
    }
}
