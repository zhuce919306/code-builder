package com.pl.code.core.template;

import com.pl.code.core.GeneratorUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClasssName ClassTemplateData
 * @Description 类模板数据
 * @Author liuds
 * @Date 2021/5/25
 * @Version V0.0.1
 */
@Data
public class ClassTemplateData extends TemplateData {
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

    // 是否继承父类
    private Boolean extendsSupperClass;
    // 父类包名
    private String superClassPackageName;
    // 父类名称
    private String superClassName;
    // 父类泛型
    private List<PackageDataModel> superGenericList;
    // 实现类
    private List<PackageDataModel> implementsList;
    // 字段
    private List<FieldTemplateData> fieldList;
    // 注解列表
    private List<AnnotationTemplateData> annotations;
    // 包名列表
    private List<String> importPackages;
    // 是否装配
    private boolean wired;
    // 装配类型
    private String wiredType;


    /**
     * 添加字段
     *
     * @param field
     * @return
     */
    public ClassTemplateData addField(FieldTemplateData field) {
        if (fieldList == null) {
            fieldList = new ArrayList<>();
        }
        fieldList.add(field);
        return this;
    }

    /**
     * 添加实现类
     * @param packageModel
     * @return
     */
    public ClassTemplateData addImplement(PackageDataModel packageModel) {
        if (implementsList == null) {
            implementsList = new ArrayList<>();
        }
        if (packageModel != null ){
            implementsList.add(packageModel);
        }

        return this;
    }

    /**
     * 添加泛型
     *
     * @param packageModel
     * @return
     */
    public ClassTemplateData addGenericClass(PackageDataModel packageModel) {
        if (superGenericList == null) {
            superGenericList = new ArrayList<>();
        }
        if (packageModel != null ){
            superGenericList.add(packageModel);
        }

        return this;
    }

    public ClassTemplateData addAnnotation(String name,String propertiesText) {
        if (this.annotations == null) {
            this.annotations = new ArrayList<>();
        }
        AnnotationTemplateData annotation = new AnnotationTemplateData();
        annotation.setName(name);
        annotation.formatProperties(propertiesText);
        annotations.add(annotation);
        return this;
    }

    public ClassTemplateData setSuperClassPackageName(String packageName) {
        this.superClassPackageName = packageName;
        if (StringUtils.isNotBlank(packageName)) {
            PackageDataModel packageModel = GeneratorUtil.packageToClassName(packageName,null);
            this.superClassName = packageModel.getClassName();
        } else {
            this.superClassName = null;
            this.extendsSupperClass = false;
        }
        return this;
    }



}
