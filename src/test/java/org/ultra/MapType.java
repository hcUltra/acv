package org.ultra;

import lombok.Data;

/**
 * @author yinger
 * @description TODO
 * @date 2024/1/10 18:12
 **/
@Data
public class MapType implements JavaType {
    // 全类名
    private String fullClassName;

    private JavaType keyActuallyType;

    private JavaType valueActuallyType;

    private int size;

    public MapType(String fullClassName) {
        this.fullClassName = fullClassName;
    }
}
