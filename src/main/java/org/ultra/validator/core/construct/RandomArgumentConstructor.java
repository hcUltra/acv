package org.ultra.validator.core.construct;

import org.ultra.validator.common.util.ReflectUtil;
import org.ultra.validator.config.ArgumentConfig;
import org.ultra.validator.config.ArgumentsConfig;
import org.ultra.validator.enums.ArgumentTypeEnum;
import org.ultra.validator.exception.UnableResolveTypeException;
import org.ultra.validator.factory.BasicTypeFactory;
import org.ultra.validator.range.Range;
import org.ultra.validator.common.util.SortUtil;


import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author hcUltra
 * @description TODO 将生成的组数抽象出来，以便加入多组对照样本
 * @date 2024/4/23 18:39
 **/
@SuppressWarnings("all")
public class RandomArgumentConstructor {
    // 根据参数配置 ArgumentsConfig 生成随机参数
    // 返回两组随机参数 args0 ... argsN
    public static Object[] constructor(ArgumentConfig config) throws UnableResolveTypeException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // 判断类型
        if (config.getArgumentType().equals(ArgumentTypeEnum.PRIMITIVE)) {
            // 递归出口
            // 基本数据类型 -> 返回两个基本数据类型的变量
            return constructPrimitiveObj(config);
        } else if (config.getArgumentType().equals(ArgumentTypeEnum.WRAPPER)) {
            // 递归出口
            // Wrapper 类型 -> 返回两个基本数据类型包装类的对象（引用变量）
            return constructWrapperObj(config);
        } else if (config.getArgumentType().equals(ArgumentTypeEnum.ARRAY)) {
            // 数组类型 -> 返回两个数组对象
            return constructArrayObj(config);
        } else if (config.getArgumentType().equals(ArgumentTypeEnum.COLLECTION)) {
            // 集合类型 -> 返回两个集合
            return constructCollectionObj(config);
        } else if (config.getArgumentType().equals(ArgumentTypeEnum.MAP)) {
            // TODO 此处会尽量可能构造出两个有size个键值对的Map
            //  如果在给定的范围内无法构造出两个有size个键值对的Map，则直接抛出异常
            //   此处可能引发死循环
            return constructMapObj(config);
        }
        throw new UnableResolveTypeException("Unknown Type On Constructing Random Parameter");
    }

    /**
     * 调度器
     *
     * @param configs 参数配置
     * @return 返回两组参数
     */
    public static Object[][] constructDispatcher(ArgumentsConfig configs) throws UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<ArgumentConfig> configList = configs.getArgumentConfigs();
        Object[][] parameters = new Object[2][configList.size()];
        int i = 0;
        for (ArgumentConfig config : configList) {
            Object[] objs = constructor(config);
            assert objs != null;
            parameters[0][i] = objs[0];
            parameters[1][i] = objs[1];
            i++;
        }
        return parameters;
    }

    public static Object[] constructPrimitiveObj(ArgumentConfig config) {
        BasicTypeFactory btf = new BasicTypeFactory(config);
        return BasicTypeFactory.factory.get(config.getType().toString()).apply(btf);
    }

    public static Object[] constructWrapperObj(ArgumentConfig config) {
        BasicTypeFactory btf = new BasicTypeFactory(config);
        return BasicTypeFactory.factory.get(config.getType().toString().substring(6)).apply(btf);
    }

    public static Object[] constructArrayObj(ArgumentConfig config) throws UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Object[] arrays = new Object[2];
        int size = config.getFixSize() == -1 ? (int)Range.getNumberMinToMax(config.getSize()) : config.getFixSize();
        if (size < 0) {
            return null;
        }
        Class<?> arrayType = Class.forName(config.getClassName());
        Class<?> componentType = arrayType.getComponentType();
        arrays[0] = Array.newInstance(componentType, size);
        arrays[1] = Array.newInstance(componentType, size);
        int index = 0;
        for (int i = 0; i < size; i++) {
            Object[] objs = constructor(config.getInnerConfig()[0]);
            assert objs != null;
            Array.set(arrays[0], index, objs[0]);
            Array.set(arrays[1], index, objs[1]);
            index++;
        }

        if (config.getCollation() != null && config.getCollation().getIsSorted()) {
            // TODO 对数据进行排序 暂时只支持基本数据类型(maybe enough)
            SortUtil.sort(arrays[0], config.getCollation().getComparator());
            SortUtil.sort(arrays[1], config.getCollation().getComparator());
        }
        return arrays;
    }

    public static Object[] constructCollectionObj(ArgumentConfig config) throws UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object[] collections = new Object[2];
        int size = config.getFixSize() == -1 ? (int)Range.getNumberMinToMax(config.getSize()) : config.getFixSize();
        if (size < 0) {
            return null;
        }
        collections[0] = ReflectUtil.getNonParameterInstance(config.getClassName());
        collections[1] = ReflectUtil.getNonParameterInstance(config.getClassName());
        for (int i = 0; i < size; i++) {
            Object[] objs = constructor(config.getInnerConfig()[0]);
            assert objs != null;
            ((Collection) (collections[0])).add(objs[0]);
            ((Collection) (collections[1])).add(objs[1]);
        }

        if (config.getCollation() != null && config.getCollation().getIsSorted()) {
            List list1;
            List list2;
            if (collections[0] instanceof List) {
                list1 = (List) collections[0];
                list2 = (List) collections[1];
                list1.sort(config.getCollation().getComparator());
                list2.sort(config.getCollation().getComparator());
            } else {
                list1 = new ArrayList(((Collection) (collections[0])));
                list2 = new ArrayList(((Collection) (collections[1])));
                list1.sort(config.getCollation().getComparator());
                list2.sort(config.getCollation().getComparator());
                ((Collection) (collections[0])).clear();
                ((Collection) (collections[1])).clear();
                ((Collection) (collections[0])).addAll(list1);
                ((Collection) (collections[1])).addAll(list2);
            }
        }
        return collections;
    }

    public static Object[] constructMapObj(ArgumentConfig config) throws UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object[] maps = new Object[2];
        int size = config.getFixSize() == -1 ? (int)Range.getNumberMinToMax(config.getSize()) : config.getFixSize();
        if (size < 0) {
            return null;
        }
        // 构造两个Map
        maps[0] = ReflectUtil.getNonParameterInstance(config.getClassName());
        maps[1] = ReflectUtil.getNonParameterInstance(config.getClassName());
        // 获取足够数量的 Key -> Value
        for (int i = 0; i < size; i++) {
            // 返回两个建
            Object[] keys = constructor(config.getInnerConfig()[0]);
            Object[] values = constructor(config.getInnerConfig()[1]);
            assert keys != null;
            assert values != null;
            ((Map) ((maps[0]))).put(keys[0], values[0]);
            ((Map) ((maps[1]))).put(keys[1], values[1]);
        }

        // 如果没有集齐足够的key，那么继续构造
        int count = size - ((Map) (maps[0])).size();
        if (count > 0) {
            do {
                // 继续尽最大努力构造key
                Object[] keys = constructor(config.getInnerConfig()[0]);
                assert keys != null;
                if (!((Map) (maps[0])).containsKey(keys[0])) {
                    Object[] values = constructor(config.getInnerConfig()[1]);
                    assert values != null;
                    ((Map) (maps[0])).put(keys[0], values[0]);
                    ((Map) (maps[1])).put(keys[1], values[1]);
                    count--;
                }
            } while (count > 0);
        }
        return maps;
    }
}