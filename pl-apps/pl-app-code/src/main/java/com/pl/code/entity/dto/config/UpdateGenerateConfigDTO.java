package com.pl.code.entity.dto.config;

import com.pl.code.entity.po.PlGenerateConfig;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClasssName UpdateGenerateConfigDTO
 * @Description 修改生成配置参数DTO
 * @Author Liuyh
 * @Date 2021/5/15
 * @Version V0.0.1
 */
@Data
public class UpdateGenerateConfigDTO extends AbstractGenerateConfigDTO<UpdateGenerateConfigDTO> {

    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * dto 转 po
     *
     * @return
     */
    public PlGenerateConfig dto2po() {
        PlGenerateConfig po = super.dto2po();
        po.setId(id);
        return po;
    }
}