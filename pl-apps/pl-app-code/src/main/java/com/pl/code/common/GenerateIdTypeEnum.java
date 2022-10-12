package com.pl.code.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @ClasssName GenerateIdTypeEnum
 * @Description
 * @Author liuds
 * @Date 2021/5/25
 * @Version V0.0.1
 */
@AllArgsConstructor
@Getter
public enum GenerateIdTypeEnum {
    // 自增
    AUTO("IdType.AUTO"),
    // 未设置主键
    NONE("IdType.NONE"),
    // 输入
    INPUT("IdType.INPUT"),
    // 雪花算法ID
    ASSIGN_ID("IdType.ASSIGN_ID"),
    // UUID
    ASSIGN_UUID("IdType.ASSIGN_UUID");
    private String value;
    public boolean nameEquals(String value){
        return this.name().equalsIgnoreCase(value);
    }

    public static GenerateIdTypeEnum nameOf(String value){
        return Arrays.stream(GenerateIdTypeEnum.values()).filter(item -> item.nameEquals(value)).findFirst().orElse(null);
    }
}
