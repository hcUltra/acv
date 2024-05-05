package org.ultra.validator.config;

import lombok.Data;
import org.ultra.validator.enums.ArgumentTypeEnum;
import org.ultra.validator.range.Range;

import java.lang.reflect.Type;

/**
 * 局部参数配置 - 配置单个参数
 */
@Data
public class ArgumentConfig {
    // 递归
    private ArgumentConfig[] innerConfig;

    private Integer iThArgument = 0;    // 第个参数 只有根才有该属性                 i
    private Integer depth = 0;          // 深度                                   j
    private Integer iThCollection = 0;  // 当前深度的第i个集合 Map 会产生分支         k

    Integer fixSize = -1;// 针对某一次测试的大小

    // 全限定类型 or 数组签名 配合 ArgumentType 使用
    private String className;
    // 参数类型 -     PRIMITIVE,WRAPPER,CLASS,ARRAY,MAP,COLLECTION
    private ArgumentTypeEnum argumentType = null;
    // for parse
    private Type type;
    // 数据元素的取值范围
    private Range value = null;
    // 数据集合大小f
    private Range size = null;
    // 针对String做出的适配  字符有哪些字符构成 -> 字符约束
    private String chars = null;
    // 集合是否有序
    private Order order = null;
    // TODO 集合的相邻两数不相等（适用于内部是基本类型的集合）
}