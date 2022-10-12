package com.pl.code.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @ClasssName CodeStyleEnum
 * @Description
 * @Author Liuyh
 * @Date 2021/5/25
 * @Version V0.0.1
 */
@AllArgsConstructor
@Getter
public enum CodeStyleEnum {
    MYBATIS("mybatis"),
    MYBATIS_PLUS("mybatis-plus"),
    PL_WEB("pl-web");
    private String value;

    public boolean valueEquals(String value){
        return this.value.equalsIgnoreCase(value);
    }

    public static CodeStyleEnum ofValue(String value){
        return Arrays.stream(CodeStyleEnum.values()).filter(item -> item.valueEquals(value)).findFirst().orElse(null);
    }
}
