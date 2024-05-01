package org.ultra.validator.config;

import lombok.Data;
import org.ultra.validator.enums.ArgumentTypeEnum;
/**
 * @author hcUltra
 * @description
 * @date 2024/4/30 21:34
 **/
@Data
public class Argument {
    private ArgumentTypeEnum type;
    private String className;
    private Long size;
}
