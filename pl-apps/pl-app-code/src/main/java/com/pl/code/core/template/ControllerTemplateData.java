package com.pl.code.core.template;

import lombok.Data;

/**
 * @ClasssName ControllerTemplateData
 * @Description
 * @Author liuds
 * @Date 2021/6/1
 * @Version V0.0.1
 */
@Data
public class ControllerTemplateData extends ClassTemplateData{

    private boolean basicApi;
    private String responsePackageName;
    private String responseClassName;
    private String successMethod;
    private String errorMethod;

    private String pkJavaType;
    private String pkName;
}
