package org.ultra.validator.demo2;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.ultra.validator.common.util.UnsafeUtil;
import sun.misc.Unsafe;

/**
 * @author zhoufy
 * @date 2019年2月2日 上午10:29:15
 */
@SuppressWarnings("restriction")        //取消显示的警告集
public class ObjectsAddressDemo {

    static final Unsafe unsafe = getUnsafe();
    static final boolean is64bit = true; // auto detect if possible.

    public static void main(String... args) throws NoSuchFieldException {
        Integer a = new Integer(123);

        Object[] o = new Object[10];
        Arrays.fill(o, new Object());
        printAddresses("Address", o);

        long offset = unsafe.objectFieldOffset(a.getClass().getDeclaredField("value"));
        System.out.println(Long.toHexString(UnsafeUtil.getUnsafeInstance().getInt(a, offset)));
        print(a);
        System.out.println("-----------GC前------------");
        print(a);


    }

    private static void print(Object a) {
        //通过sun.misc.Unsafe;
        printAddresses("Address", a);
    }

    @SuppressWarnings("deprecation")
    public static void printAddresses(String label, Object... objects) {
        System.out.print(label + ":         0x");
        long last = 0;
        int offset = unsafe.arrayBaseOffset(objects.getClass());
        int scale = unsafe.arrayIndexScale(objects.getClass());
        switch (scale) {
            case 4:
                long factor = is64bit ? 8 : 1;
                final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                System.out.print(Long.toHexString(i1));
                last = i1;
                for (int i = 1; i < objects.length; i++) {
                    final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                    if (i2 > last)
                        System.out.print(", +" + Long.toHexString(i2 - last));
                    else
                        System.out.print(", -" + Long.toHexString(last - i2));
                    last = i2;
                }
                break;
            case 8:
                throw new AssertionError("Not supported");
        }
        System.out.println();
    }

    static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    /**
     * @author zhoufy
     * @date 2019年2月2日 上午10:29:15
     */
    @SuppressWarnings("restriction")        //取消显示的警告集
    public static class _ObjectsAddressDemo {

        static final Unsafe unsafe = getUnsafe();
        static final boolean is64bit = true; // auto detect if possible.

        public static void main(String... args) {
            Object a = new Object();
            UnsafeUtil.setHashcode(a, 0x12345678);
            System.out.println(Long.toHexString(UnsafeUtil.getUnsafeInstance().getLong(a, 0)));
            System.out.println(Long.toHexString(UnsafeUtil.getUnsafeInstance().getLong(a, 4)));
            System.out.println(Long.toHexString(UnsafeUtil.getUnsafeInstance().getLong(a, 8)));
            System.out.println(Long.toHexString(UnsafeUtil.getUnsafeInstance().getLong(a, 12)));
            System.out.println(Long.toHexString(UnsafeUtil.getUnsafeInstance().getLong(a, 16)));
            System.out.println(Long.toHexString(UnsafeUtil.getUnsafeInstance().getLong(a, 20)));
            System.out.println(Long.toHexString(UnsafeUtil.getUnsafeInstance().getLong(a, 24)));


            //GC前
            System.out.println("-----------GC前------------");
            Unsafe unsafe = UnsafeUtil.getUnsafeInstance();

            print(a);

            System.gc();

            //GC后
            System.out.println("-----------GC后------------");
            print(a);
        }

        private static void print(Object a) {
            //通过sun.misc.Unsafe;
            printAddresses("Address", a);

            System.out.println("HashCode:         " + Long.toHexString(a.hashCode()));

            //通过jol工具包打印对象的地址
            //        System.out.println(GraphLayout.parseInstance(a).toPrintable());
//            System.out.println("Current address: " + Long.toHexString(VM.current().addressOf(a)));
        }

        @SuppressWarnings("deprecation")
        public static void printAddresses(String label, Object... objects) {
            System.out.print(label + ":         0x");
            long last = 0;
            int offset = unsafe.arrayBaseOffset(objects.getClass());

            int scale = unsafe.arrayIndexScale(objects.getClass());
            switch (scale) {
                case 4:
                    long factor = is64bit ? 8 : 1;
                    final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                    System.out.print(Long.toHexString(i1));
                    last = i1;
                    for (int i = 1; i < objects.length; i++) {
                        final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                        if (i2 > last)
                            System.out.print(", +" + Long.toHexString(i2 - last));
                        else
                            System.out.print(", -" + Long.toHexString(last - i2));
                        last = i2;
                    }
                    break;
                case 8:
                    throw new AssertionError("Not supported");
            }
            System.out.println();
        }

        private static Unsafe getUnsafe() {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                return (Unsafe) theUnsafe.get(null);
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        }
    }
}