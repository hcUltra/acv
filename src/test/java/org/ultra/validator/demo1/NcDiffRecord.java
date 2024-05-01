package org.ultra.validator.demo1;

import java.io.Serializable;

public class NcDiffRecord implements Serializable {
    
    private Integer id;
    
    private Byte isDeleted;
 
    
    private Integer diffDependId;
 
    private String newDiffItemName;
 
    private String oldDiffItemName;
    private String expectValue;
    private String actualValue;
 
}