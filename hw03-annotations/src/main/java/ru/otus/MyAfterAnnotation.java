package ru.otus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
@Retention(RetentionPolicy.RUNTIME)
@Target(value = METHOD)
public @interface MyAfterAnnotation {

}
