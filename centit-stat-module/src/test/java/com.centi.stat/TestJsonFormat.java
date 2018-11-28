package com.centi.stat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class TestJsonFormat {

    public static void main(String[] args) {

        String jsonStr = "[{'1':'一'}, {'2':'二'}]";
        JSONArray arr = JSON.parseArray(jsonStr);
        System.out.println(jsonFormat("3", arr));

    }

    public static String jsonFormat(String key, JSONArray arr){
        for(Object o :arr){
            String value = ((JSONObject)o).getString(key);
            if(StringUtils.isNotBlank(value)){
                return value;
            }
        }
        return key;
    }
}
