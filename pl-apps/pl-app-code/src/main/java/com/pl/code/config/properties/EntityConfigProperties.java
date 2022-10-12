package com.pl.code.config.properties;

import lombok.Data;

import java.util.List;

/**
 * @ClasssName EntityConfigProperties
 * @Description 实体配置属性
 * @Author Liuyh
 * @Date 2021/5/20
 * @Version V0.0.1
 */
@Data
public class EntityConfigProperties {
    // 是否继承父类
    private boolean extendsSuper;
    // 父类包路径
    private String superClassPackage;
    // 父类泛型包路径
    private List<String> superClassGenericPackage;
    // lombok配置
    private LombokConfigProperties lombokConfig;
}
