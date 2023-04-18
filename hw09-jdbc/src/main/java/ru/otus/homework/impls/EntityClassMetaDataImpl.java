package ru.otus.homework.impls;

import ru.otus.crm.model.IdAnnotation;
import ru.otus.homework.EntityClassMetaData;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    Class<T> reflectedClass;



    public EntityClassMetaDataImpl(T clazz) {
        reflectedClass = (Class<T>) clazz;
    }

    @Override
    public String getName() {
        return reflectedClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        List<Field> fields = getAllFields();

        for(var c : reflectedClass.getConstructors()){
            if(c.getParameters().length == fields.size()){
                return (Constructor<T>) c;
            }
        }
        return null;
    }

    @Override
    public Field getIdField() {

        for(Field f : reflectedClass.getDeclaredFields()){
            if(f.isAnnotationPresent(IdAnnotation.class)){
                return f;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {

        return Arrays.stream(reflectedClass.getDeclaredFields()).toList();

    }

    @Override
    public List<Field> getFieldsWithoutId() {

        List<Field> fields = new ArrayList<>();

        for(Field f : reflectedClass.getDeclaredFields()){
            if(!f.isAnnotationPresent(IdAnnotation.class)){
                fields.add(f);
            }
        }
        return fields;
    }
}
