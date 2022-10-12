package com.pl.code.core.template;


import com.pl.data.core.entity.AbstractPlEntity;

/**
 * @ClasssName TemplateEntity
 * @Description
 * @Author Liuyh
 * @Date 2021/5/25
 * @Version V0.0.1
 */

public class TemplateData extends AbstractPlEntity {
    // 原始名称
    private String originalName;
    // 注释
    private String comment;
    // 首字母大写
    private String firstUpperCaseName;
    // 首字母大写
    private String firstLowerCaseName;
    // 驼峰
    private String humpName;

    public TemplateData() {
    }

    public TemplateData(String originalName, String comment, String firstUpperCaseName, String firstLowerCaseName, String humpName) {
        this.originalName = originalName;
        this.comment = comment;
        this.firstUpperCaseName = firstUpperCaseName;
        this.firstLowerCaseName = firstLowerCaseName;
        this.humpName = humpName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFirstUpperCaseName() {
        return firstUpperCaseName;
    }

    public void setFirstUpperCaseName(String firstUpperCaseName) {
        this.firstUpperCaseName = firstUpperCaseName;
    }

    public String getFirstLowerCaseName() {
        return firstLowerCaseName;
    }

    public void setFirstLowerCaseName(String firstLowerCaseName) {
        this.firstLowerCaseName = firstLowerCaseName;
    }

    public String getHumpName() {
        return humpName;
    }

    public void setHumpName(String humpName) {
        this.humpName = humpName;
    }
}
