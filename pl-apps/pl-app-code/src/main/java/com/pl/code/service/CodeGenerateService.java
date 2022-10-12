package com.pl.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @ClasssName CodeGenerateService
 * @Description 代码生成相关业务
 * @Author Liuyh
 * @Date 2021/5/13
 * @Version V0.0.1
 */
public interface CodeGenerateService {

    /**
     * 分页查询表
     * @param page 分页信息
     * @param tableName 表名
     * @param dsName 数据源ID
     * @return
     */
    IPage<List<Map<String, Object>>> getPage(Page page, String tableName, String dsName);
}
