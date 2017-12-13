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
public class JsonDocxContext implements IContext{
    private JSONObject docObject;

    public JsonDocxContext(){
        docObject = null;
    }

    public JsonDocxContext(Object object){
        if(object instanceof JSONObject) {
            this.docObject = (JSONObject) object;
        }else{
            this.docObject = (JSONObject) JSON.toJSON(object);
        }
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
        if(docObject==null) {
            docObject = new JSONObject();
        }
        docObject.put(key, value);
        return null;
        //throw new IllegalAccessError("Unsupported!");
    }

    /**
     * Get the Java object with the given key which was registered and null otherwise.
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        Object value = null;
        if(docObject!=null) {
            value = ReflectionOpt.attainExpressionValue(docObject, key);
        }
        return value == null? ( key.startsWith("___") ? null:new JsonDocxContext()): value;
    }

    /**
     * @param contextMap
     */
    @Override
    public void putMap(Map<String, Object> contextMap) {
        if(contextMap instanceof JSONObject) {
            this.docObject = (JSONObject) contextMap;
        }else{
            this.docObject = (JSONObject) JSON.toJSON(contextMap);
        }
        //throw new IllegalAccessError("Unsupported!");
    }

    @Override
    public Map<String, Object> getContextMap() {
        return docObject;
    }

    @Override
    public String toString(){
        return docObject==null ? "" : docObject.toJSONString();
    }
}
