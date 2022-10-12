package com.pl.code.entity.bo;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pl.code.config.properties.*;
import com.pl.common.utils.CollectionUtil;
import lombok.Data;

import java.util.List;

/**
 * @ClasssName PlGenerateConfigBO
 * @Description
 * @Author liuds
 * @Date 2021/5/27
 * @Version V0.0.1
 */
@Data
public class PlGenerateConfigBO {
    // 包名
    private String packageName;
    // 作者名称
    private String author;
    // 表前缀
    private String tablePrefix;
    // 代码风格
    private String style;
    // 是否装配
    private boolean wired;
    // 装配类型
    private String wiredType;
    // swagger
    private boolean swagger;
    // 类型映射
    private List<TypeMapperConfigProperties> typeMapperConfig;
    // 字段配置
    private ColumnConfig columnConfig;
    // 实体配置
    private EntityConfig entityConfig;
    // Mapper配置
    private MapperConfig mapperConfig;
    // Controller配置
    private ControllerConfigProperties controllerConfig;

    public static PlGenerateConfigBO parse(String text){
        if (StrUtil.isNotBlank(text)){
            JSONObject json = JSON.parseObject(text);
            PlGenerateProperties properties = json.toJavaObject(PlGenerateProperties.class);
            return parse(properties);
        }
        return null;
    }

    public static PlGenerateConfigBO parse(PlGenerateProperties properties){
        PlGenerateConfigBO config = new PlGenerateConfigBO();
        config.setPackageName(properties.getPackageName());
        config.setStyle(properties.getStyle());
        config.setAuthor(properties.getAuthor());
        config.setTablePrefix(properties.getTablePrefix());
        config.setTypeMapperConfig(properties.getTypeMapperConfig());
        config.setColumnConfig(ColumnConfig.parse(properties.getColumnConfig()));
        config.setEntityConfig(EntityConfig.parse(properties.getEntityConfig()));
        config.setMapperConfig(MapperConfig.parse(properties.getMapperConfig()));
        config.setWired(properties.isWired());
        config.setWiredType(properties.getWiredType());
        config.setControllerConfig(properties.getControllerConfig());
        config.setSwagger(properties.isSwagger());
        return config;
    }

    @Data
    public static class ColumnConfig {
        // 公共字段
        private String commonColumns;
        // 隐藏字段
        private String hiddenColumns;
        // 逻辑删除字段
        private String logicDeleteColumns;
        // insert语句自动填充字段
        private String insertFillColumns;
        // update语句自动填充字段
        private String updateFillColumns;
        // insert或update语句自动填充字段
        private String insertOrUpdateFillColumns;

        public static ColumnConfig parse(ColumnConfigProperties properties){
            ColumnConfig config =  new ColumnConfig();
            config.setCommonColumns(CollectionUtil.join(properties.getCommonColumns(),","));
            config.setHiddenColumns(CollectionUtil.join(properties.getHiddenColumns(),","));
            config.setLogicDeleteColumns(CollectionUtil.join(properties.getLogicDeleteColumns(),","));
            config.setInsertFillColumns(CollectionUtil.join(properties.getInsertFillColumns(),","));
            config.setUpdateFillColumns(CollectionUtil.join(properties.getUpdateFillColumns(),","));
            config.setInsertOrUpdateFillColumns(CollectionUtil.join(properties.getInsertOrUpdateFillColumns(),","));
            return config;
        }
    }

    @Data
    public static class EntityConfig {
        // 是否继承父类
        private boolean extendsSuper;
        // 父类包路径
        private String superClassPackage;
        // 父类泛型包路径
        private String superClassGenericPackage;
        // lombok配置
        private LombokConfigProperties lombokConfig;

        public static EntityConfig parse(EntityConfigProperties properties){
            EntityConfig config = new EntityConfig();
            config.setExtendsSuper(properties.isExtendsSuper());
            config.setSuperClassPackage(properties.getSuperClassPackage());
            config.setSuperClassGenericPackage(CollectionUtil.join(properties.getSuperClassGenericPackage(),","));
            config.setLombokConfig(properties.getLombokConfig());
            return config;
        }
    }

    @Data
    public static class MapperConfig {
        private String idType;
        private String method;
        private boolean buildXml;
        private boolean mapperAnnotation;

        public static MapperConfig parse(MapperConfigProperties properties){
            MapperConfig config = new MapperConfig();
            config.setIdType(properties.getIdType());
            config.setMethod(CollectionUtil.join(properties.getMethod(),","));
            config.setBuildXml(properties.isBuildXml());
            config.setMapperAnnotation(properties.isMapperAnnotation());
            return config;
        }
    }
}