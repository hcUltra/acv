package org.ultra.validator.common.map;

import org.eclipse.collections.api.block.HashingStrategy;
import org.eclipse.collections.impl.map.strategy.mutable.UnifiedMapWithHashingStrategy;
import org.ultra.validator.common.util.UnsafeUtil;

public class AddressMap {
    private static final HashingStrategy<Object> address = new HashingStrategy<Object>() {
        /**
         * 该方法会返回Object hashCode 方法的返回结果，无论Object类的hashCode方法是否被重写
         * 开启虚拟机参数，-XX:+UnlockExperimentalVMOptions -XX:hashCode=4 使得虚拟机返回对象的地址值，保证哈希值唯一（此处取舍，放弃性能）
         * 此处保证哈希值唯一，也不是一定的，此处拿到的对象地址是64位long类型截断为32位的结果
         */
        @Override
        public int computeHashCode(Object o) {
            return System.identityHashCode(o);
        }

        /**
         * 只有当两个对象的地址值完全相等时，才认为两个对象相等
         */
        @Override
        public boolean equals(Object o, Object e1) {
            return System.identityHashCode(o) == System.identityHashCode(e1);
        }
    };
    public static final UnifiedMapWithHashingStrategy<Object, String> addressMap = UnifiedMapWithHashingStrategy.newMap(address);

    public static void main(String[] args) {
        // for test
        Integer a = new Integer(1);
        Integer b = new Integer(1);
        System.out.println(Integer.toHexString(UnsafeUtil.hashcode(a)));
        System.out.println(Integer.toHexString(UnsafeUtil.hashcode(b)));
        System.out.println("========");
        System.out.println(Integer.toHexString(System.identityHashCode(a)));
        System.out.println(Integer.toHexString(System.identityHashCode(b)));
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println("========");
        System.out.println(Integer.toHexString(UnsafeUtil.hashcode(a)));
        System.out.println(Integer.toHexString(UnsafeUtil.hashcode(b)));
        System.out.println("========");
//        System.out.println(Integer.toHexString(System.identityHashCode(b)));
//        addressMap.put(a, "a-" + UUID.randomUUID());
//        addressMap.put(b, "b-" + UUID.randomUUID());
//        System.out.println("========get=======");
//        System.out.println(addressMap.get(a));
//        System.out.println(addressMap.get(b));
    }
}
