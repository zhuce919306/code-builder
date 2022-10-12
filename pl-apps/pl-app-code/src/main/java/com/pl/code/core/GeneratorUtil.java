package com.pl.code.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.pl.code.config.properties.TypeMapperConfigProperties;
import com.pl.code.core.template.PackageDataModel;
import com.pl.core.exception.BusinessException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClasssName GeneratorUtil
 * @Description
 * @Author liuds
 * @Date 2021/5/29
 * @Version V0.0.1
 */
@UtilityClass
public class GeneratorUtil {

    /**
     * 添加包列表
     *
     * @param packages
     * @param packageName
     */
    public void putPackage(List<String> packages, String packageName) {
        if (StringUtils.isBlank(packageName)) {
            return;
        }
        if (packages == null) {
            packages = new ArrayList<>();
        }
        if (!packages.contains(packageName)) {
            packages.add(packageName);
        }
    }

    /**
     * 添加包列表
     *
     * @param packages
     * @param packageModel
     */
    public void putPackage(List<String> packages, PackageDataModel packageModel) {
        if (packageModel != null && StringUtils.isBlank(packageModel.getPackageName())) {
            return;
        }
        putPackage(packages, packageModel.getPackageName());
    }

    /**
     * 包名转类型
     *
     * @param packageName
     * @param thisClassName
     * @return
     */
    public PackageDataModel packageToClassName(String packageName, String thisClassName) {
        if (StringUtils.isBlank(packageName)) {
            return null;
        }
        if ("this".equalsIgnoreCase(packageName)) {
            if (StringUtils.isBlank(thisClassName)) {
                throw new BusinessException("未知泛型类名");
            }
            return PackageDataModel.builder().className(firstUpperCaseAndHump(thisClassName)).build();
        } else {
            if (StringUtils.isNotBlank(thisClassName)) {

                return PackageDataModel.builder()
                        .className(firstUpperCaseAndHump(thisClassName))
                        .packageName(packageName)
                        .build();
            } else {

                return PackageDataModel.builder()
                        .className(firstUpperCaseAndHump(packageName.substring(packageName.lastIndexOf(".") + 1)))
                        .packageName(packageName)
                        .build();
            }
        }
    }

    /**
     * 判断列名是否存在
     *
     * @param columns
     * @param column
     * @return
     */
    public boolean columnExist(List<String> columns, String column) {
        if (CollectionUtil.isEmpty(columns)) {
            return false;
        }
        List<String> columnList = new ArrayList<>();
        columns.forEach(item -> {
            columnList.add(item.toLowerCase());
        });
        return columns.contains(column.toLowerCase());
    }


    /**
     * 数据库类型转Java类型
     *
     * @param typeMapperConfig
     * @param type
     * @return
     */
    public TypeMapperConfigProperties colType2JavaType(List<TypeMapperConfigProperties> typeMapperConfig, String type) {
        TypeMapperConfigProperties defaultType = new TypeMapperConfigProperties();
        defaultType.setJavaType("Object");
        if (StrUtil.isNotBlank(type) && CollectionUtil.isNotEmpty(typeMapperConfig)) {
            TypeMapperConfigProperties typeConfig = typeMapperConfig.stream().filter(item -> item.getColumnType().equalsIgnoreCase(type)).findFirst().orElse(defaultType);
            return typeConfig;
        } else {
            return defaultType;
        }
    }


    /**
     * 表名转换成Java类名
     */
    public String tableToClsName(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return col2java(tableName);
    }


    /**
     * 下划线转驼峰
     */
    public String col2java(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 首字母小写
     *
     * @param text
     * @return
     */
    public String firstLowerCase(String text) {
        return StringUtils.uncapitalize(text);
    }

    /**
     * 首字母大写
     *
     * @param text
     * @return
     */
    public String firstUpperCase(String text) {
        return StringUtils.capitalize(text);
    }

    /**
     * 首字母小写并驼峰
     *
     * @param text
     * @return
     */
    public String firstLowerCaseAndHump(String text) {
        if (text.contains("_")) {
            text = col2java(text);
        }
        return StringUtils.uncapitalize(text);
    }

    /**
     * 首字母大写并驼峰
     *
     * @param text
     * @return
     */
    public String firstUpperCaseAndHump(String text) {
        if (text.contains("_")) {
            text = col2java(text);
        }
        return StringUtils.capitalize(text);
    }

    public static void main(String[] args) {
        System.err.println(col2java("BaseMapper"));
        System.err.println(firstUpperCaseAndHump("BaseMapper"));
    }
}
