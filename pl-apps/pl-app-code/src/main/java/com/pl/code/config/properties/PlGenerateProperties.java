package com.pl.code.config.properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @ClasssName PlGenerateProperties
 * @Description
 * @Author Liuyh
 * @Date 2021/5/20
 * @Version V0.0.1
 */
@Data
public class PlGenerateProperties {
    // 包名
    private String packageName;
    // 作者名称
    private String author;
    // 表前缀
    private String tablePrefix;
    // 代码风格
    private String style;
    // swagger
    private boolean swagger;
    // 是否装配
    private boolean wired;
    // 装配类型
    private String wiredType;
    // 类型映射
    private List<TypeMapperConfigProperties> typeMapperConfig;
    // 字段配置
    private ColumnConfigProperties columnConfig;
    // 实体配置
    private EntityConfigProperties entityConfig;
    // Mapper配置
    private MapperConfigProperties mapperConfig;
    // Controller配置
    private ControllerConfigProperties controllerConfig;
}
