package com.pl.code.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pl.code.entity.po.PlDataSource;

/**
 * @ClasssName DatasourceService
 * @Description 数据源业务
 * @Author Liuyh
 * @Date 2021/5/12
 * @Version V0.0.1
 */
public interface DatasourceService extends IService<PlDataSource> {

    /**
     * 新增数据源
     * @param dataSource
     * @return
     */
    boolean saveDatasource(PlDataSource dataSource);

    /**
     * 更新数据源
     * @param dataSource
     * @return
     */
    boolean updateDatasource(PlDataSource dataSource);

    /**
     * 删除数据源
     * @param ids
     * @return
     */
    boolean removeDatasource(Long ... ids);
}
