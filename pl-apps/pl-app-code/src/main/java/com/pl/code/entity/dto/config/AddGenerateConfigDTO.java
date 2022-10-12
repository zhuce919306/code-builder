package com.pl.code.entity.dto.config;

import com.pl.code.entity.po.PlGenerateConfig;

/**
 * @ClasssName AddGenerateConfigDTO
 * @Description 新增生成配置参数DTO
 * @Author Liuyh
 * @Date 2021/5/15
 * @Version V0.0.1
 */
public class AddGenerateConfigDTO extends AbstractGenerateConfigDTO<AddGenerateConfigDTO> {

    /**
     * vo 转 po
     *
     * @return
     */
    public PlGenerateConfig dto2po() {
        PlGenerateConfig po = super.dto2po();
        return po;
    }
}