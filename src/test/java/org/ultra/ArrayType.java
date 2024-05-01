package org.ultra;

import lombok.Data;

/**
 * @author yinger
 * @description TODO
 * @date 2024/1/10 18:08
 **/
@Data
public class ArrayType implements JavaType {

    private String signature;// 类型签名

    private JavaType componentType;// 数组元素类型全限定名

    public ArrayType(String signature) {
        this.signature = signature;
    }
}