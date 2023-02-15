package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Ioc {

    public Ioc() {
    }

    TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;

        private final ArrayList <Method> al;

        DemoInvocationHandler(TestLoggingInterface myClass) {
            al = Arrays.stream(myClass.getClass().getDeclaredMethods()).collect(Collectors.toCollection(ArrayList::new));
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            for (Method m : al) {
                if (m.isAnnotationPresent(Log.class) && (Arrays.stream(m.getParameters()).toList()+m.getName()).equals(Arrays.stream(method.getParameters()).toList() + method.getName())) {
                    System.out.print("executed method: " + method.getName() + ", params: ");
                    for (Object o : args) {
                        System.out.print(o + " ");
                    }
                    System.out.println(" ");
                    break;
                }
            }
            return method.invoke(myClass, args);
        }
    }
}
