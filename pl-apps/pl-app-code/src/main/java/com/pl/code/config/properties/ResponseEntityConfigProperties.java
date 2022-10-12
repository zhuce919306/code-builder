package com.pl.code.config.properties;

import lombok.Data;

/**
 * @ClasssName ResponseEntityConfigProperties
 * @Description
 * @Author liuds
 * @Date 2021/6/1
 * @Version V0.0.1
 */
@Data
public class ResponseEntityConfigProperties {
    private String packageName;
    private String successMethod;
    private String errorMethod;
}
