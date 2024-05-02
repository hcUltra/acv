package org.ultra.validator.common.util;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.ultra.validator.annotation.CorrectMethod;
import org.ultra.validator.annotation.Validator;
import org.ultra.validator.annotation.ValidatorMethod;
import org.ultra.validator.config.ArgumentsConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * @author yinger
 * @description 封装反射操作
 * @date 2024/1/2 20:23
 **/
public class ReflectUtil {
    /**
     * 根据类和方法名获取方法
     *
     * @param clazz      类对象
     * @param methodName 方法名
     * @return 返回匹配的方法，如果没有匹配的方法则返回null
     */
    public static Method getMethod(Class<?> clazz, String methodName) {
        return // 1. 使用Java 8的流式API来获取和筛选方法
                Arrays.stream(clazz.getDeclaredMethods()).
                        // 2. 使用instanceof操作符来匹配方法名
                                filter(method -> methodName.equals(method.getName())).
                        // 3. 如果找到了匹配的方法，则返回该方法
                                findFirst().orElse(null);
    }

    /**
     * 根据完整的类名字符串获取无参实例对象。
     *
     * @param fullClassNameStr 完整的类名字符串，格式为 "fully.qualified.ClassName"。
     * @return 无参实例对象
     * @throws ClassNotFoundException 当找不到指定的类时抛出此异常
     * @throws NoSuchMethodException  当找不到指定的方法时抛出此异常
     * @throws InstantiationException 当类是抽象类、接口或数组类时或发生其他不可逆错误时抛出此异常
     * @throws IllegalAccessException 当类或类成员处于访问冲突中时抛出此异常
     */
    public static Object getNonParameterInstance(String fullClassNameStr) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // 参数校验：确保类名字符串非空
        if (fullClassNameStr == null || fullClassNameStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to instantiate the collection");
        }
        // 获取类对象
        Class<?> clazz = Class.forName(fullClassNameStr);
        // 获取无参构造函数
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        // 允许调用私有构造函数
        constructor.setAccessible(true);
        return constructor.newInstance();
    }


    /**
     * 使用反射调用类的方法
     *
     * @param args 方法的参数列表
     * @return 方法的返回值
     */
    public static Object invoke(Method method, Object... args) {
        Class<?> clazz = ArgumentsConfig.clazz;

        try {
            // 反射获取构造方法
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object o = constructor.newInstance();
            // 调用方法
            return method.invoke(o, args);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            // 更详细的错误信息
            String errorMsg = "Failed to invoke method: " + method.getName() + " in class: " + clazz.getName() +
                    ". Exception: " + e.getMessage();
            System.out.println(errorMsg);
            throw new RuntimeException(errorMsg, e);
        }
    }

    public static Method reflectValidatorAnnotationMethod() {
        Class<?> clazz = ArgumentsConfig.clazz;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ValidatorMethod.class)) {
                return method;
            }
        }
        throw new RuntimeException("No found method annotated with @ValidatorMethod found in class " + clazz.getName());
    }

    public static Method reflectCorretAnnotationMethod() {
        Class<?> clazz = ArgumentsConfig.clazz;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(CorrectMethod.class)) {
                return method;
            }
        }
        throw new RuntimeException("No found method annotated with @CorrectMethod found in class " + clazz.getName());
    }

    public static Class<?> scanValidatorAnnotation(String packageName) {
        // 配置Reflections
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()));

        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Validator.class);


        if (annotatedClasses.size() > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            annotatedClasses.forEach((cls) -> {
                stringBuilder.append(cls.getName()).append(" ");
            });
            throw new RuntimeException("Found more than one @Validator annotated" + "\n" + stringBuilder);
        }

        return annotatedClasses.iterator().next();
    }
}
