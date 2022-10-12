package com.pl.datasource.dynamic.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @ClasssName ConnectionTypeEnum
 * @Description 数据库连接类型
 * @Author liuds
 * @Date 2021/7/8
 * @Version V0.0.1
 */
@AllArgsConstructor
@Getter
public enum ConnectionTypeEnum {
    /**
     * 主机连接
     */
    HOST(0),

    /**
     * URL连接
     */
    URL(1);
    private Integer type;

    public static ConnectionTypeEnum get(Integer value){
        return Arrays.stream(ConnectionTypeEnum.values()).filter(item -> item.getType().equals(value)).findFirst().orElse(null);
    }

    public boolean typeEquals(Integer value){
        return this.getType().equals(value);
    }
}
