package com.pl.code.entity.po;

import lombok.Data;

/**
 * @ClasssName TableInfo
 * @Description 表信息
 * @Author liuds
 * @Date 2021/5/17
 * @Version V0.0.1
 */
@Data
public class TableInfo {
    private String tableName;
    private String tableComment;
    private String createTime;
}
