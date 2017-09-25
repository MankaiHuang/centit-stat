package com.centit.support.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centit.support.algorithm.ReflectionOpt;
import fr.opensagres.xdocreport.template.IContext;

import java.util.Map;

/**
 * Created by codefan on 17-9-25.
 * 测试没有通过
 */
public class ObjectDocxContext implements IContext{
    private Object docObject;

    public ObjectDocxContext(Object object){
        this.docObject = object;
    }

    /**
     * Register a Java object with the given key.
     *
     * @param key  主键
     * @param value Java model
     * @return
     */
    @Override
    public Object put(String key, Object value) {
        throw new IllegalAccessError("Unsupported!");
    }

    /**
     * Get the Java object with the given key which was registered and null otherwise.
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        Object value = ReflectionOpt.attainExpressionValue(docObject,key);
        return value == null ? key : value;
    }

    /**
     * @param contextMap
     */
    @Override
    public void putMap(Map<String, Object> contextMap) {
        throw new IllegalAccessError("Unsupported!");
    }

    @Override
    public Map<String, Object> getContextMap() {
        return (JSONObject) JSON.toJSON(docObject);
    }
}
