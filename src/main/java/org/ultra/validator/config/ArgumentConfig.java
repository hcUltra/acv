package org.ultra.validator.config;

import lombok.Data;
import org.ultra.validator.enums.ArgumentTypeEnum;
import org.ultra.validator.range.Range;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;

/**
 * 局部参数配置 - 配置单个参数
 */
@Data
public class ArgumentConfig {

    private Integer iThArgument = 0;    // 第个参数 只有根才有该属性                 i
    private Integer depth = 0;          // 深度                                   j
    private Integer iThCollection = 0;  // 当前深度的第i个集合 Map 会产生分支         k

    public int maxI;
    public int maxJ;
    public int maxK;


    // 递归
    private ArgumentConfig[] innerConfig;
    // 参数名称 - 主要用于解决参数之间的依赖关系如 a.length < b.length
    private String name = null;
    // 全限定类型 or 数组签名 配合 ArgumentType 使用
    private String className;
    // 参数类型 -     PRIMITIVE,WRAPPER,CLASS,ARRAY,MAP,COLLECTION
    private ArgumentTypeEnum argumentType = null;
    // for parse
    private Type type;
    // 数据元素的取值范围
    private Range value = null;
    // 数据集合大小
    private Range size = null;
    // 针对String做出的适配  字符有哪些字符构成
    private String allowedCharacters = null;
    // 集合是否有序
    private Collation collation = null;
    // 依赖于哪个参数 要在依赖参数之后进行生成
    private String dependsOn = null;
    // TODO 暂时不知道怎么使用
    //  private Boolean uniqueElements; // 元素是否唯一
    //   特定值列表
    //  1 2 -> 1 出现两次
    // 特定值列表 ? 考虑词频
    private List<SpecificValue> specificValues = null;

    // TODO 暂时不知道如何使用
    //    private Structure structure; // 数据结构的特定要求(适配一些常用的数据结构）
    // 是否合适 ？ 去重 内部使用set去重
    // TODO 暂时不知道如何使用
    Boolean distinct = false;

    // 链式配置语法
    public ArgumentConfig withDependsOn(String depends) {
        this.dependsOn = depends;
        return this;
    }

    public ArgumentConfig withName(String name) {
        this.name = name;
        return this;
    }

    public ArgumentConfig withSize(Range size) {
        this.size = size;
        return this;
    }

    public ArgumentConfig withParameterType(ArgumentTypeEnum type) {
        this.argumentType = type;
        return this;
    }

    public ArgumentConfig withValue(Range valueRange) {
        this.value = valueRange;
        return this;
    }

    public ArgumentConfig withAllowedCharacters(String allowedCharacters) {
        this.allowedCharacters = allowedCharacters;
        return this;
    }


    public ArgumentConfig withSpecificValues(List<SpecificValue> specificValues) {
        this.specificValues = specificValues;
        return this;
    }

    public ArgumentConfig withCollation(Collation collation) {
        this.collation = collation;
        return this;
    }

    public ArgumentConfig withSorted(boolean isSorted, Comparator comparator) {
        Collation collation = new Collation();
        collation.setIsSorted(isSorted);
        collation.setComparator(comparator);
        return this;
    }


    public ArgumentConfig withInnerConfig(ArgumentConfig[] innerConfig) {
        this.innerConfig = innerConfig;
        return this;
    }
}