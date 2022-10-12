package com.pl.code.config.properties;

import lombok.Data;

/**
 * @ClasssName ControllerConfigProperties
 * @Description
 * @Author Liuyh
 * @Date 2021/6/1
 * @Version V0.0.1
 */
@Data
public class ControllerConfigProperties {
    private boolean basicApi;
    private ResponseEntityConfigProperties responseEntity;
}
