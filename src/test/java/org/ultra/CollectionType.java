package org.ultra;

import lombok.Data;

/**
 * @author yinger
 * @description TODO
 * @date 2024/1/10 18:09
 **/
@Data
public class CollectionType implements JavaType {
    private String fullClassName;

    private JavaType actuallyType;

    private int size;
    public CollectionType(String fullClassName) {
        this.fullClassName = fullClassName;
    }
}
