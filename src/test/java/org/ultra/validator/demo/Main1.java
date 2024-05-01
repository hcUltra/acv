//package org.zhc.acvserver.demo;
//
//import java.lang.annotation.*;
//import java.lang.reflect.Method;
//
//public class Main1 {
//    public static void main(String[] args) {
//        // 获取类的Class对象
//        Class<?> clazz = MyClass.class;
//        // 获取类的所有方法
//        Method[] methods = clazz.getDeclaredMethods();
//        // 循环遍历所有方法
//        for (Method method : methods) {
//            // 判断方法上是否有特定的注解
//            if (method.isAnnotationPresent(MyAnnotation.class)) {
//                // 获取注解信息
//                MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
//                // 输出方法名和注解信息
//                System.out.println("方法名：" + method.getName() + "，注解信息：" + myAnnotation.value());
//            }
//        }
//    }
//}
//
//// 定义一个注解
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
//@interface MyAnnotation1 {
//    String value();
//}
//
//// 定义一个类，其中的方法被注解所作用
//class MyClass {
//    @MyAnnotation1("注解信息")
//    public void myMethod() {
//        // 方法体
//    }
//
//    public void anotherMethod() {
//        // 方法体
//    }
//}