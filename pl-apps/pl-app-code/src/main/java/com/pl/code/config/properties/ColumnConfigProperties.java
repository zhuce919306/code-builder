package com.pl.code.config.properties;

import lombok.Data;

import java.util.List;

/**
 * @ClasssName ColumnConfigProperties
 * @Description 字段配置属性
 * @Author Liuyh
 * @Date 2021/5/20
 * @Version V0.0.1
 */
@Data
public class ColumnConfigProperties {
    // 公共字段
    private List<String> commonColumns;
    // 隐藏字段
    private List<String> hiddenColumns;
    // 逻辑删除字段
    private List<String> logicDeleteColumns;
    // insert语句自动填充字段
    private List<String> insertFillColumns;
    // update语句自动填充字段
    private List<String> updateFillColumns;
    // insert或update语句自动填充字段
    private List<String> insertOrUpdateFillColumns;
}
