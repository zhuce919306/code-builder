package com.pl.datasource.dynamic.constants;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @ClasssName UrlPatternEnum
 * @Description 数据库URL格式枚举
 * @Author Liuyh
 * @Date 2021/7/8
 * @Version V0.0.1
 */
@AllArgsConstructor
@Getter
public enum UrlPatternEnum {
    /**
     * mysql数据库
     */
    MYSQL("mysql", "jdbc:mysql://{}:{}/{}?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=true&useSSL=false&serverTimezone=UTC&serverTimezone=GMT%2b8"),

    /**
     * pg数据库
     */
    PG("pg", "jdbc:postgresql://{}:{}/{}"),

    /**
     * sqlserver
     */
    SQLSERVER("sqlserver", "jdbc:sqlserver://{}\\{}:{};database={};characterEncoding=UTF-8"),

    /**
     * oracle
     */
    ORACLE("oracle", "jdbc:oracle:thin:@{}:{}:{}"),

    /**
     * db2
     */
    DB2("db2", "jdbc:db2://{}:{}/{}");

    private String name;
    private String pattern;

    public boolean nameEquals(String name) {
        return this.name.equals(name);
    }

    public String of(String... args) {
        return StrUtil.format(this.pattern, args);
    }

    public static UrlPatternEnum get(String name) {
        return Arrays.stream(UrlPatternEnum.values()).filter(item -> item.nameEquals(name)).findFirst().orElse(null);
    }
}
