package com.pl.code.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pl.code.mapper.CodeGenerateMapper;
import com.pl.code.service.CodeGenerateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClasssName CodeGenerateServiceImpl
 * @Description 代码生成相关业务
 * @Author Liuyh
 * @Date 2021/5/13
 * @Version V0.0.1
 */
@Service
public class CodeGenerateServiceImpl implements CodeGenerateService {

    @Resource
    private CodeGenerateMapper codeGenerateMapper;

    /**
     * 分页查询表
     *
     * @param page      分页信息
     * @param tableName 表名
     * @param dsName      数据源ID
     * @return
     */
    @Override
    @DS("#last")
    public IPage<List<Map<String, Object>>> getPage(Page page, String tableName, String dsName) {
        return codeGenerateMapper.selectTableList(page, tableName);
    }
}
