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

        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            ArrayList<Method> al = Arrays.stream(myClass.getClass().getDeclaredMethods()).collect(Collectors.toCollection(ArrayList::new));
            String s1 = Arrays.stream(method.getParameterTypes()).toList() + method.getName();

            for (Method m : al) {
                if (m.getParameters().length == method.getParameters().length) {

                    String s2 = Arrays.stream(m.getParameterTypes()).toList() + method.getName();

                    if(s1.equals(s2)){
                        if(m.isAnnotationPresent(Log.class)){
                            System.out.print("executed method: " + method.getName() + ", params: ");
                            for(Object o : args){
                                System.out.print(o + " ");
                            }
                            System.out.println(" ");
                            break;
                        }
                    }

                }
            }
            return method.invoke(myClass, args);
        }
    }
}
