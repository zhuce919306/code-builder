package com.pl.code.config.properties;

import lombok.Data;

/**
 * @ClasssName TypeMapperConfigProperties
 * @Description
 * @Author liuds
 * @Date 2021/5/25
 * @Version V0.0.1
 */
@Data
public class TypeMapperConfigProperties {
    private String columnType;
    private String javaType;
    private boolean importPackage;
    private String packageName;
}
