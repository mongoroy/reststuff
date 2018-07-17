package org.roy.reststuff.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

@Retention(RUNTIME)
//@BindingAnnotation
@Target(ElementType.METHOD)
public @interface CacheWithChangeStream {
}
