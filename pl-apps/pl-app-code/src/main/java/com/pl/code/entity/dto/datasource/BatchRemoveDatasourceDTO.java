package com.pl.code.entity.dto.datasource;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @ClasssName BatchRemoveDatasourceDTO
 * @Description 批量删除数据源DTO
 * @Author liuds
 * @Date 2021/5/13
 * @Version V0.0.1
 */
@Data
public class BatchRemoveDatasourceDTO {

    @NotEmpty(message = "数据源id不能为空")
    private List<Long> ids;
}