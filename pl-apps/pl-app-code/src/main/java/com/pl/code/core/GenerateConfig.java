package com.pl.code.core;

import com.alibaba.fastjson.JSON;
import com.pl.code.config.properties.PlGenerateProperties;
import com.pl.code.entity.dto.generate.GenerateDTO;
import com.pl.code.entity.po.PlGenerateConfig;
import lombok.Data;

import java.util.List;

/**
 * @ClasssName GenerateConfig
 * @Description
 * @Author liuds
 * @Date 2021/5/17
 * @Version V0.0.1
 */
@Data
public class GenerateConfig extends PlGenerateProperties {
    private String dsName;
    private String tableName;
    private String moduleName;
    private String codeAnnotation;
    private List<String> templates;

    public static GenerateConfig parse(GenerateDTO dto, PlGenerateConfig configPO) {
        GenerateConfig generateConfig = JSON.parseObject(configPO.getConfigContent(), GenerateConfig.class);
        generateConfig.setDsName(dto.getDsName());
        generateConfig.setTableName(dto.getTableName());
        generateConfig.setTablePrefix(dto.getTablePrefix());
        generateConfig.setPackageName(dto.getPackageName());
        generateConfig.setModuleName(dto.getModuleName());
        generateConfig.setCodeAnnotation(dto.getCodeAnnotation());
        generateConfig.setTemplates(dto.getTemplates());
        generateConfig.setStyle(dto.getStyle());
        generateConfig.setWired(dto.isWired());
        generateConfig.setWiredType(dto.getWiredType());
        generateConfig.setSwagger(dto.isSwagger());
        return generateConfig;
    }

}
