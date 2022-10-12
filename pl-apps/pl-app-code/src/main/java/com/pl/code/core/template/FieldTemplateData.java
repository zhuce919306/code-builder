package com.pl.code.core.template;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClasssName FieldTemplateData
 * @Description 字段
 * @Author Liuyh
 * @Date 2021/5/25
 * @Version V0.0.1
 */
@Data
@Builder
public class FieldTemplateData extends TemplateData {
    // 字段类型
    private String type;

    // 注解
    private List<AnnotationTemplateData> annotations;

    // 是否需要导包
    private boolean importPackage;
    // 包名
    private String packageName;

    // 是否为公共极端
    private boolean common;
    // 是否隐藏
    private boolean hidden;
    // 是否为逻辑删除字段
    private boolean logicDelete;
    // 是否insert自动填充
    private boolean insertFill;
    // 是否update自动填充
    private boolean updateFill;
    // 是否insert或update自动填充
    private boolean insertOrUpdateFill;
    // 是否为主键
    private boolean pk;
    private String columnType;

    /**
     * @param name           注解名称 @VoField
     * @param propertiesText 注解属性 name=名称&value=值
     * @return
     */
    public FieldTemplateData addAnnotation(String name, String propertiesText) {
        if (this.annotations == null) {
            this.annotations = new ArrayList<>();
        }
        AnnotationTemplateData annotation = new AnnotationTemplateData();
        annotation.setName(name);
        annotation.formatProperties(propertiesText);
        annotations.add(annotation);
        return this;
    }

    public FieldTemplateData addAnnotation(String name, String propertiesText, boolean isAdd) {
        if (isAdd) {
            addAnnotation(name, propertiesText);
        }
        return this;
    }


}