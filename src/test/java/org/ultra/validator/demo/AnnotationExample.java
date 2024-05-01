package org.ultra.validator.demo;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class AnnotationExample {

    @MyAnnotation()
    public void sayHello() {
        System.out.println("Hello");
    }

    public static void main(String[] args) throws Exception {
        AnnotationExample example = new AnnotationExample();
        Method method = example.getClass().getMethod("sayHello");
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            System.out.println(annotation.annotationType().getName() + ": " + annotation.toString());
        }
    }
}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String value() default "";
}
