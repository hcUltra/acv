package org.ultra;

import lombok.Data;

/**
 * @author yinger
 * @description 叶子
 * @date 2024/1/10 18:55
 **/
@Data
public class ClassType implements JavaType {
    private String fullClassName;

    public ClassType(String fullClassName) {
        this.fullClassName = fullClassName;
    }
}
