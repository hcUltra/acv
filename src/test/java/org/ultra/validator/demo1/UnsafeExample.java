//package org.zhc.acvserver;
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//
//
///**
// * 写引用
// */
//public class UnsafeExample {
//    public static void main(String[] args) throws Exception {
//        // 获取Unsafe实例
//        Unsafe unsafe = getUnsafeInstance();
//
//        // 创建一个对象
//        Object obj = new Object();
//
//        // 获取对象的内存地址
//        long address = unsafe.allocateMemory(obj.getClass().);
//
//        // 将对象内的内存地址写入到指定的存地址
//        unsafe.copyMemory(obj, Unsafe.ARRAY_BYTE_BASE_OFFSET, address, Unsafe.ARRAY_BYTE_BASE_OFFSET);
//
//        // 从指定的内存地址读取对象
//        Object readObj = unsafe.getObject(address);
//
//        // 释放内存
//        unsafe.freeMemory(address);
//
//        System.out.println("Original object: " + obj);
//        System.out.println("Read object: " + readObj);
//    }
//
//    private static Unsafe getUnsafeInstance() throws Exception {
//        Field field = Unsafe.class.getDeclaredField("theUnsafe");
//        field.setAccessible(true);
//        return (Unsafe) field.get(null);
//    }
//}
