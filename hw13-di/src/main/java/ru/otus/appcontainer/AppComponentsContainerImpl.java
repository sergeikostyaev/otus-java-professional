package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.config.AppConfig;
import ru.otus.services.GameProcessor;
import ru.otus.services.GameProcessorImpl;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {
    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        Object clazz = null;
        try {
            clazz = configClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        checkConfigClass(configClass);
        var finalClazz = clazz;
        duplicateCheck(configClass);

        Arrays.stream(configClass.getDeclaredMethods()).filter(element -> element.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(element -> element.getAnnotation(AppComponent.class).order())).forEach(m -> {
                    try {
                        getArgs(m);
                        var invoked = m.invoke(finalClazz, getArgs(m));
                        appComponents.add(invoked);
                        appComponentsByName.put(m.getAnnotation(AppComponent.class).name().toLowerCase(), invoked);
                        System.out.println(m.getReturnType().getSimpleName().toLowerCase());

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        System.out.println(appComponents.size());
    }

    private Object[] getArgs(Method m) {
        var params = m.getParameters();
        List<Object> args = new ArrayList<>();
        Arrays.stream(params).forEach(e -> args.add(appComponentsByName.get(e.getType().getSimpleName().toLowerCase())));

        return args.toArray();
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        var clazz = (C) appComponentsByName.get(componentClass.getSimpleName().toLowerCase());
        if (clazz != null) {
            return clazz;
        } else {
            var list = appComponents.stream().filter(e -> e.getClass().isAssignableFrom(componentClass)).collect(Collectors.toList());
            if (list.size() > 1) {
                throw new RuntimeException();
            }
            return (C) list.get(0);
        }
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        var object = (C) appComponentsByName.get(componentName.toLowerCase());
        if (object == null) {
            throw new RuntimeException();
        }
        return object;
    }

    private void duplicateCheck(Class<?> clazz) {
        List<String> names = new ArrayList<>();
        Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(AppComponent.class)).forEach(m -> {
            if (names.contains(m.getAnnotation(AppComponent.class).name())) {
                throw new RuntimeException("Duplicate");
            }
            names.add(m.getAnnotation(AppComponent.class).name());
        });
    }
}


