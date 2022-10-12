package com.pl.code.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pl.code.entity.po.TableColumn;
import com.pl.code.entity.po.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClasssName CodeGenerateMapper
 * @Description 代码生成mapper
 * @Author Liuyh
 * @Date 2021/5/12
 * @Version V0.0.1
 */
@Mapper
public interface CodeGenerateMapper {
    /**
     * 分页查询表格
     *
     * @param page      分页信息
     * @param tableName 表名称
     * @return
     */
    IPage<List<Map<String, Object>>> selectTableList(Page page, @Param("tableName") String tableName);

    /**
     * 查询表信息
     *
     * @param tableName 表名称
     * @param dsName    数据源名称
     * @return
     */
    @DS("#last")
    TableInfo selectTableInfo(@Param("tableName") String tableName, String dsName);

    /**
     * 分页查询表分页信息
     *
     * @param page
     * @param tableName
     * @param dsName
     * @return
     */
    @DS("#last")
    IPage<TableColumn> selectTableColumnPage(Page page, @Param("tableName") String tableName, @Param("dsName") String dsName);


    @DS("#last")
    List<TableColumn> selectTableColumn(@Param("tableName") String tableName, @Param("dsName") String dsName);
}