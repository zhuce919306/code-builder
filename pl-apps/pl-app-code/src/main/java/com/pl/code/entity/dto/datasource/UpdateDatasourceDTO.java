package com.pl.code.entity.dto.datasource;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClasssName UpdateDatasourceDTO
 * @Description 更新数据源DTO
 * @Author Liuyh
 * @Date 2021/5/13
 * @Version V0.0.1
 */
@Data
public class UpdateDatasourceDTO extends AddDatasourceDTO{

    /**
     * 数据源id
     */
    @NotNull(message = "数据源id不能为空")
    private Long id;
}