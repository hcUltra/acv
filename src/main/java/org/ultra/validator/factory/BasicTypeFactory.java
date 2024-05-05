package org.ultra.validator.factory;

import org.ultra.validator.common.map.AddressMap;
import org.ultra.validator.config.ArgumentConfig;
import org.ultra.validator.range.Range;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author hcUltra
 * @description 构造基本数据类型的对象或者变量
 * @date 2024/4/25 19:36
 **/
public class BasicTypeFactory {
    private final Range valueRange;
    private final String allowedCharacters;

    public BasicTypeFactory(ArgumentConfig config) {
        this.valueRange = config.getValue();
        this.allowedCharacters = config.getChars();
    }

    // 传入一个字符串，表示基本类型的数据，获取一个基本类型的对象数组，包含两个基本类型的对象
    // 注册中心
    public static Map<String, Function<BasicTypeFactory, Object[]>> factory = new HashMap();

    static {
        // 注册
        factory.put("java.lang.Byte", btf -> {
            Object[] result = new Object[2];
            byte byteValue = (byte) (Range.getNumberMinToMax(btf.valueRange));
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < result.length; i++) {
                result[i] = new Byte(byteValue);
                AddressMap.addressMap.put(result[i], uuid);
            }
            return result;
        });
        factory.put("java.lang.Short", btf -> {
            Object[] result = new Object[2];
            short shortValue = (short) (Range.getNumberMinToMax(btf.valueRange));
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < result.length; i++) {
                result[i] = new Short(shortValue);
                AddressMap.addressMap.put(result[i], uuid);
            }
            return result;
        });
        factory.put("java.lang.Integer", btf -> {
            Object[] result = new Object[2];
            int intValue = (int) (Range.getNumberMinToMax(btf.valueRange));
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < result.length; i++) {
                result[i] = new Integer(intValue);
                AddressMap.addressMap.put(result[i], uuid);
            }
            return result;
        });
        factory.put("java.lang.Long", btf -> {
            Object[] result = new Object[2];
            int intValue = (int) (Range.getNumberMinToMax(btf.valueRange));
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < result.length; i++) {
                result[i] = new Integer(intValue);
                AddressMap.addressMap.put(result[i], uuid);
            }
            return result;
        });
        factory.put("java.lang.Float", btf -> {
            Object[] result = new Object[2];
            float floatValue = (float) (Range.getNumberMinToMax(btf.valueRange));
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < result.length; i++) {
                result[i] = new Float(floatValue);
                AddressMap.addressMap.put(result[i], uuid);
            }
            return result;
        });
        factory.put("java.lang.Double", btf -> {
            Object[] result = new Object[2];
            double doubleValue = Range.getNumberMinToMax(btf.valueRange);
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < result.length; i++) {
                result[i] = new Double(doubleValue);
                AddressMap.addressMap.put(result[i], uuid);
            }
            return result;
        });
        factory.put("java.lang.Character", btf -> {
            Object[] result = new Object[2];
            char charValue;
            String uuid = UUID.randomUUID().toString();
            if (btf.allowedCharacters != null) {
                // 说明开启了 allowedCharacters 配置 在允许出现的字符范围内做等概率随机
                int index = (int) Range.getNumberMinToMax(new Range(0, btf.allowedCharacters.length() - 1));
                charValue = btf.allowedCharacters.charAt(index);
            } else {
                charValue = (char) Range.getNumberMinToMax(btf.valueRange);
            }
            for (int i = 0; i < result.length; i++) {
                result[i] = new Character(charValue);
                AddressMap.addressMap.put(result[i], uuid);
            }
            return result;
        });
        factory.put("java.lang.Boolean", btf -> {
            Object[] result = new Object[2];
            boolean booleanValue = Math.random() < 0.5;
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < result.length; i++) {
                result[i] = Boolean.valueOf(booleanValue);
                AddressMap.addressMap.put(result[i], uuid);
            }
            return result;
        });
        factory.put("byte", btf -> {
            Object[] result = new Object[2];
            byte byteValue = (byte) Range.getNumberMinToMax(btf.valueRange);
            Arrays.fill(result, byteValue);
            return result;
        });
        factory.put("short", btf -> {
            Object[] result = new Object[2];
            short shortValue = (short) Range.getNumberMinToMax(btf.valueRange);
            Arrays.fill(result, shortValue);
            return result;
        });
        factory.put("int", btf -> {
            Object[] result = new Object[2];
            int intValue = (int) Range.getNumberMinToMax(btf.valueRange);
            Arrays.fill(result, intValue);
            return result;
        });
        factory.put("long", btf -> {
            Object[] result = new Object[2];
            long longValue = (long) Range.getNumberMinToMax(btf.valueRange);
            Arrays.fill(result, longValue);
            return result;
        });
        factory.put("float", btf -> {
            Object[] result = new Object[2];
            float floatValue = (float) Range.getNumberMinToMax(btf.valueRange);
            Arrays.fill(result, floatValue);
            return result;
        });
        factory.put("double", btf -> {
            Object[] result = new Object[2];
            double doubleValue = Range.getNumberMinToMax(btf.valueRange);
            Arrays.fill(result, doubleValue);
            return result;
        });
        factory.put("char", btf -> {
            Object[] result = new Object[2];
            char charValue;
            if (btf.allowedCharacters != null) {
                // 说明开启了 allowedCharacters配置 在允许出现的字符范围内做等概率随机
                int index = (int) Range.getNumberMinToMax(new Range(0, btf.allowedCharacters.length() - 1));
                charValue = btf.allowedCharacters.charAt(index);
            } else {
                charValue = (char) Range.getNumberMinToMax(btf.valueRange);
            }
            Arrays.fill(result, charValue);
            return result;
        });
        factory.put("boolean", btf -> {
            Object[] result = new Object[2];
            boolean booleanValue = Math.random() < 0.5;
            Arrays.fill(result, booleanValue);
            return result;
        });
    }
}
