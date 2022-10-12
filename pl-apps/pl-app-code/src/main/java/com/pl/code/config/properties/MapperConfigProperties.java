package com.pl.code.config.properties;

import lombok.Data;

import java.util.List;

/**
 * @ClasssName MapperConfigProperties
 * @Description Mapper配置属性
 * @Author liuds
 * @Date 2021/5/20
 * @Version V0.0.1
 */
@Data
public class MapperConfigProperties {
    private String idType;
    private boolean buildXml;
    private boolean mapperAnnotation;
    private List<String> method;
}
