package com.pl.code.config.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClasssName LombokConfigProperties
 * @Description lombok配置属性
 * @Author liuds
 * @Date 2021/5/20
 * @Version V0.0.1
 */
@Data
public class LombokConfigProperties {
    private Boolean enable;
    private Boolean builder;
    private Boolean data;
    private Boolean getter;
    private Boolean setter;
    private Boolean toStr;
    private Boolean equalsAndHashCode;
    private Boolean allArgsConstructor;
    private Boolean noAllArgsConstructor;
    private Accessors accessors;


    @Data
    public static class Accessors {
        private Boolean enable;
        private String mode;
        private String prefixModeValue;
    }

    public boolean hasAnnotation() {
        return enable && (builder || data || getter || setter || toStr || equalsAndHashCode || allArgsConstructor || noAllArgsConstructor || isEnableAccessors());
    }

    public boolean isEnableAccessors() {
        return accessors != null && accessors.enable;
    }

    public String getAccessorsPropertity() {
        if (StringUtils.isBlank(accessors.mode)) {
            return null;
        }
        if ("prefix".equalsIgnoreCase(accessors.mode)) {
            if (StringUtils.isBlank(accessors.prefixModeValue)){
                return null;
            } else {
                return "prefix=" + accessors.prefixModeValue;
            }

        }
        return accessors.mode + "=true";

    }
}
