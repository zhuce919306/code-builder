package com.pl.code.core.template;

import cn.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClasssName AnnotationTemplateData
 * @Description 注解模板数据
 * @Author Liuyh
 * @Date 2021/5/25
 * @Version V0.0.1
 */
public class AnnotationTemplateData {
    private String name;
    private List<KeyValueTemplateData> properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<KeyValueTemplateData> getProperties() {
        return properties;
    }

    public void setProperties(List<KeyValueTemplateData> properties) {
        this.properties = properties;
    }
    /**
     * 添加注解属性
     * @param key
     * @param value
     * @return
     */
    public AnnotationTemplateData addProperty(String key, String value) {
        if (this.properties == null) {
            this.properties = new ArrayList<>();
        }
        KeyValueTemplateData keyValue = KeyValueTemplateData
                .builder()
                .key(key)
                .value(value)
                .build();
        this.properties.add(keyValue);
        return this;
    }

    /**
     * 格式化注解属性
     * @param text
     * @return
     */
    public AnnotationTemplateData formatProperties(String text) {
        if (StringUtils.isNotBlank(text)) {
            Map<String, String> propertyMap = HttpUtil.decodeParamMap(text, "utf-8");
            for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                addProperty(key, value);
            }
        }
        return this;
    }
}
