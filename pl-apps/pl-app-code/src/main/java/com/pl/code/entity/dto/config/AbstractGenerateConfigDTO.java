package com.pl.code.entity.dto.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pl.code.config.properties.ColumnConfigProperties;
import com.pl.code.config.properties.EntityConfigProperties;
import com.pl.code.config.properties.MapperConfigProperties;
import com.pl.code.config.properties.PlGenerateProperties;
import com.pl.code.entity.bo.PlGenerateConfigBO;
import com.pl.code.entity.po.PlGenerateConfig;
import com.pl.common.utils.CollectionUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClasssName AbstractGenerateConfigDTO
 * @Description
 * @Author liuds
 * @Date 2021/6/1
 * @Version V0.0.1
 */
@Data
public abstract class AbstractGenerateConfigDTO<T extends AbstractGenerateConfigDTO> {

    private String name;
    private String configContent;

    /**
     * dto 转 po
     *
     * @return
     */
    public PlGenerateConfig dto2po() {
        char separator = ',';
        PlGenerateConfig po = new PlGenerateConfig();
        PlGenerateConfigBO bo = JSONObject.parseObject(this.getConfigContent(), PlGenerateConfigBO.class);
        PlGenerateProperties properties = new PlGenerateProperties();
        properties.setPackageName(bo.getPackageName());
        properties.setAuthor(bo.getAuthor());
        properties.setTablePrefix(bo.getTablePrefix());
        properties.setTypeMapperConfig(bo.getTypeMapperConfig());
        properties.setStyle(bo.getStyle());
        properties.setWired(bo.isWired());
        properties.setWiredType(bo.getWiredType());
        properties.setSwagger(bo.isSwagger());
        properties.setControllerConfig(bo.getControllerConfig());

        // ColumnConfigProperties
        PlGenerateConfigBO.ColumnConfig columnConfigBO = bo.getColumnConfig();
        ColumnConfigProperties columnConfig = new ColumnConfigProperties();
        columnConfig.setCommonColumns(StrUtil.split(columnConfigBO.getCommonColumns(),separator));
        columnConfig.setHiddenColumns(StrUtil.split(columnConfigBO.getHiddenColumns(),separator));
        columnConfig.setLogicDeleteColumns(StrUtil.split(columnConfigBO.getLogicDeleteColumns(),separator));
        columnConfig.setInsertFillColumns(StrUtil.split(columnConfigBO.getInsertFillColumns(),separator));
        columnConfig.setUpdateFillColumns(StrUtil.split(columnConfigBO.getUpdateFillColumns(),separator));
        columnConfig.setInsertOrUpdateFillColumns(StrUtil.split(columnConfigBO.getInsertOrUpdateFillColumns(),separator));
        properties.setColumnConfig(columnConfig);

        // EntityConfigProperties
        PlGenerateConfigBO.EntityConfig entityConfigBo = bo.getEntityConfig();
        EntityConfigProperties entityConfig = new EntityConfigProperties();
        entityConfig.setExtendsSuper(entityConfigBo.isExtendsSuper());
        entityConfig.setSuperClassPackage(entityConfigBo.getSuperClassPackage());
        entityConfig.setSuperClassGenericPackage(StrUtil.split(entityConfigBo.getSuperClassGenericPackage(),separator));
        entityConfig.setLombokConfig(entityConfigBo.getLombokConfig());
        properties.setEntityConfig(entityConfig);

        // MapperConfigProperties
        PlGenerateConfigBO.MapperConfig mapperConfigBO = bo.getMapperConfig();
        MapperConfigProperties mapperConfig = new MapperConfigProperties();
        mapperConfig.setIdType(mapperConfigBO.getIdType());
        mapperConfig.setBuildXml(mapperConfigBO.isBuildXml());
        mapperConfig.setMethod(StrUtil.split(mapperConfigBO.getMethod(),','));
        mapperConfig.setMapperAnnotation(mapperConfigBO.isMapperAnnotation());
        properties.setMapperConfig(mapperConfig);
        po.setName(name);
        po.setConfigContent(JSON.toJSONString(properties));
        return po;
    }
}