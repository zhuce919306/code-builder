package com.pl.code.service.impl;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pl.code.entity.po.PlDataSource;
import com.pl.code.mapper.DatasourceMapper;
import com.pl.code.service.DatasourceService;
import com.pl.core.utils.ApplicationContextUtils;
import com.pl.datasource.dynamic.constants.ConnectionTypeEnum;
import com.pl.datasource.dynamic.constants.UrlPatternEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClasssName DatasourceServiceImpl
 * @Description 数据源业务
 * @Author Liuyh
 * @Date 2021/5/12
 * @Version V0.0.1
 */
@Slf4j
@Service
public class DatasourceServiceImpl extends ServiceImpl<DatasourceMapper, PlDataSource> implements DatasourceService {
    @Resource
    private DataSourceCreator dataSourceCreator;

    /**
     * 新增数据源
     *
     * @param dataSource
     * @return
     */
    @Override
    public boolean saveDatasource(PlDataSource dataSource) {
        // 校验配置合法性
        if (!checkDataSource(dataSource)) {
            return Boolean.FALSE;
        }
        // 添加动态数据源
        addDynamicDataSource(dataSource);
        this.baseMapper.insert(dataSource);
        return Boolean.TRUE;
    }

    /**
     * 更新数据源
     *
     * @param dataSource
     * @return
     */
    @Override
    public boolean updateDatasource(PlDataSource dataSource) {
        if (!checkDataSource(dataSource)) {
            return Boolean.FALSE;
        }
        // 先移除
        DynamicRoutingDataSource dynamicRoutingDataSource = ApplicationContextUtils.getBean(DynamicRoutingDataSource.class);
        dynamicRoutingDataSource.removeDataSource(baseMapper.selectById(dataSource.getId()).getName());
        // 再添加
        addDynamicDataSource(dataSource);
        this.baseMapper.updateById(dataSource);
        return Boolean.TRUE;
    }

    /**
     * 删除数据源
     *
     * @param ids
     * @return
     */
    @Override
    public boolean removeDatasource(Long ... ids) {
        DynamicRoutingDataSource dynamicRoutingDataSource = ApplicationContextUtils.getBean(DynamicRoutingDataSource.class);
        for (int i = 0; i < ids.length; i++) {
            dynamicRoutingDataSource.removeDataSource(baseMapper.selectById(ids[i]).getName());
            this.baseMapper.deleteById(ids[i]);
        }
        return Boolean.TRUE;
    }


    /**
     * 验证数据源
     *
     * @param conf
     * @return
     */
    private Boolean checkDataSource(PlDataSource conf) {
        String url = conf.getUrl();
        if (ConnectionTypeEnum.HOST.typeEquals(conf.getConnectionType())){
            UrlPatternEnum urlPattern = UrlPatternEnum.get(conf.getDbType());
            url = urlPattern.of(conf.getHost(), conf.getPort(), conf.getDbName());
        }
        try (Connection connection = DriverManager.getConnection(url, conf.getUsername(), conf.getPassword())) {
        } catch (SQLException e) {
            log.error("数据源配置 {} , 获取链接失败", conf.getName(), e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 添加动态数据源
     * @param conf 数据源信息
     */
    public void addDynamicDataSource(PlDataSource conf) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setPoolName(conf.getName());
        dataSourceProperty.setUrl(conf.getUrl());
        dataSourceProperty.setUsername(conf.getUsername());
        dataSourceProperty.setPassword(conf.getPassword());
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);

        DynamicRoutingDataSource dynamicRoutingDataSource = ApplicationContextUtils.getBean(DynamicRoutingDataSource.class);
        dynamicRoutingDataSource.addDataSource(dataSourceProperty.getPoolName(), dataSource);
    }
}
