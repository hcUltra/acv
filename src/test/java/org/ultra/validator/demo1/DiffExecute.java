package org.ultra.validator.demo1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiffExecute {
    private static ObjectMapper objectMapper = new ObjectMapper();
 
    /**
     * 对比接口结果
     * @param carsourceObj
     * @param popObj
     * @return
     */
    public boolean diffObject(String diffName, Object carsourceObj, Object popObj, List<NcDiffRecord> diffRecords) throws JsonProcessingException {
        if (carsourceObj == null && popObj == null) {
            return true;
        }
        if (isBaseType(carsourceObj, popObj)) {
            if (carsourceObj != null && !carsourceObj.equals(popObj)) {
                addNcDiffRecord(carsourceObj, popObj, diffName, diffRecords);
                return false;
            }else if (popObj != null && !popObj.equals(carsourceObj)) {
                addNcDiffRecord(carsourceObj, popObj, diffName, diffRecords);
                return false;
            } else {
                return true;
            }
        }
        // 旧结果是list
        if (carsourceObj instanceof List) {
            // 新结果不是list报错
            if (!(popObj instanceof List)) {
                addNcDiffRecord(carsourceObj, popObj, diffName+"的类型不同"+"<oldClass>=["+carsourceObj.getClass()+"]<newClass>=["+popObj.getClass()+"]", diffRecords);
                return false;
            }
            Boolean result = true;
            List carsourList = (List) carsourceObj;
            List popList = (List) popObj;
            if (carsourList.size() != popList.size()) {
                addNcDiffRecord(carsourceObj, popObj, diffName+"的集合长度不同", diffRecords);
                return false;
            }
            for (int i = 0; i < carsourList.size(); i++) {
                result = diffObject("list", carsourList.get(i), popList.get(i), diffRecords) && result;
            }
            return result;
        }
        // 旧的不是list,新结果如果是list那么就报错
        if (popObj instanceof List) {
            addNcDiffRecord(carsourceObj, popObj, diffName+"的类型不同"+"<oldClass>=["+carsourceObj.getClass()+"]<newClass>=["+popObj.getClass()+"]", diffRecords);
            return false;
        }
 
        Boolean result = true;
        Map<String, Object> carsourceMap =
        objectMapper.readValue(objectMapper.writeValueAsString(carsourceObj), Map.class);
        Map<String, Object> popMap = objectMapper.readValue(objectMapper.writeValueAsString(popObj), Map.class);
        if (carsourceMap.size() != popMap.size()) {
            addNcDiffRecord(carsourceObj, popObj, diffName+"对象属性数量不同", diffRecords);
            return false;
        }
        Set<String> keySet = carsourceMap.keySet();
        for (String carsourceKey : keySet) {
            // 属性缺失的情况
            if (!popMap.containsKey(carsourceKey)) {
                addNcDiffRecord(carsourceMap.get(carsourceKey), null, "新结果没有属性为["+carsourceKey+"]的值", diffRecords);
                continue;
            }
            result = diffObject(carsourceKey, carsourceMap.get(carsourceKey), popMap.get(carsourceKey), diffRecords) && result;
        }
        return result;
    }
 
    private boolean isBaseType(Object carsourceObj, Object popObj) {
        if (carsourceObj instanceof String) {
            return true;
        } else if (carsourceObj instanceof Integer) {
            return true;
        } else if (carsourceObj instanceof Character) {
            return true;
        } else if (carsourceObj instanceof Boolean) {
            return true;
        } else if (carsourceObj instanceof Long){
            return true;
        } else if (carsourceObj instanceof Byte) {
            return true;
        } else if (carsourceObj instanceof Double) {
            return true;
        } else if (carsourceObj instanceof Float) {
            return true;
        } else if (carsourceObj instanceof Short){
            return true;
        } else {
            return false;
        }
    }
 
    public void addNcDiffRecord(Object carsourceObj, Object popObj, String diffName, List<NcDiffRecord> diffRecords) throws JsonProcessingException {
//        NcDiffRecord record = new NcDiffRecord();
//        record.setExpectValue(objectMapper.writeValueAsString(carsourceObj));
//        record.setActualValue(JSONObject.toJSONString(popObj));
//        record.setNewDiffItemName(diffName);
//        record.setOldDiffItemName(diffName);
//        diffRecords.add(record);
    }
 
}