package ru.otus;

import com.sun.source.util.ParameterNameProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createTestLogging() {
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

            for (Method m : al) {
                if (m.getParameters().length == method.getParameters().length) {
                    ArrayList<String> al1 = Arrays.stream(m.getParameters()).map(Parameter::toString).collect(Collectors.toCollection(ArrayList::new));
                    ArrayList<String> al2 = Arrays.stream(method.getParameters()).map(Parameter::toString).collect(Collectors.toCollection(ArrayList::new));

                    if(al1.containsAll(al2)){
                        if(m.isAnnotationPresent(Log.class)){
                            System.out.print("executed method: " + method.getName() + ", params: ");
                            for(Object o : args){
                                System.out.print(o + " ");
                            }
                            System.out.println(" ");
                        }
                    }

                }
            }

            return method.invoke(myClass, args);
        }
    }
}