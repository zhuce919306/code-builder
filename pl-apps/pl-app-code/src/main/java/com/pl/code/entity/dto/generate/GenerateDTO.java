package com.pl.code.entity.dto.generate;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClasssName GenerateDTO
 * @Description
 * @Author liuds
 * @Date 2021/5/17
 * @Version V0.0.1
 */
@Data
public class GenerateDTO {
    private String dsName;
    private String tableName;
    private String tablePrefix;
    private String author;
    // 包名
    private String packageName;
    private String moduleName;
    private String codeAnnotation;
    private Long configId;
    @NotNull(message = "请选择模板")
    private List<String> templates;
    private String style;
    // 是否装配
    private boolean wired;
    // 装配类型
    private String wiredType;
    // swagger
    private boolean swagger;
}
