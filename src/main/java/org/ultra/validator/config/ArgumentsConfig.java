package org.ultra.validator.config;

import lombok.Data;
import org.ultra.validator.main.Solution;
import org.ultra.validator.range.Range;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hcUltra
 * @description 参数配置
 * @date 2024/4/23 07:14
 **/
@Data
public class ArgumentsConfig {
    private Method validatorMethod = null;
    private Method correctMethod = null;
    public static Class<?> clazz = Solution.class;
    private int testTimes;
    private List<ArgumentConfig> argumentConfigs;
    private final List<String> constrains;
    private List<String> evaluatorConstraints = new ArrayList<>();

    public ArgumentsConfig(List<String> constrains) {
        argumentConfigs = new ArrayList<>();
        this.constrains = constrains;
    }

    @Override
    public String toString() {
        return "parameterConfigs=" + argumentConfigs;
    }

    /**
     * 获取第i个参数，深度为j的第k个集合的数据量
     * 请调用者自己保证 i j k 的合法性，内部不作校验
     *
     * @return 返回第i个参数，深度为j的第k个集合的数据量
     */
    public Range getSizeRange(int i, int j, int k) {


        return null;
    }

    /**
     * 设置第i个参数，深度为j的第k个集合的数据量
     */
    public void setSizeRange(int i, int j, int k, Range range) {

    }

    public Range getValueRange(int i, int j, int k) {

        return null;
    }

    public void setValueRange(int i, int j, int k, Range range) {

    }

}
