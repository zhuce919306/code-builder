package com.pl.code.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @ClasssName TemplateMapperEnum
 * @Description 模板映射枚举
 * @Author liuds
 * @Date 2021/5/29
 * @Version V0.0.1
 */
@AllArgsConstructor
@Getter
public enum TemplateMapperEnum {
    ENTITY("Entity","Entity.java.ftl"),
    MAPPER("Mapper","Mapper.java.ftl"),
    MAPPER_XML("MapperXML","Mapper.xml.ftl"),
    SERVICE("Service","Service.java.ftl"),
    SERVICE_IMPL("ServiceImpl","ServiceImpl.java.ftl"),
    CONTROLLER("Controller","Controller.java.ftl");
    private String name;
    private String template;

    public static TemplateMapperEnum nameOf(String value){
        return Arrays.stream(TemplateMapperEnum.values()).filter(item -> item.name.equalsIgnoreCase(value)).findFirst().orElse(null);
    }
}
