package com.pl.code.core;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClasssName GenerateResultDTO
 * @Description
 * @Author liuds
 * @Date 2021/5/18
 * @Version V0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateResult {
    @JSONField(name = "Entity")
    private String entity;

    @JSONField(name = "Mapper")
    private String mapper;

    @JSONField(name = "MapperXML")
    private String mapperXml;

    @JSONField(name = "Service")
    private String service;

    @JSONField(name = "ServiceImpl")
    private String serviceImpl;

    @JSONField(name = "Controller")
    private String controller;
}
