package com.pl.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pl.code.entity.po.PlDataSource;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClasssName DataSourceMapper
 * @Description 数据源mapper
 * @Author Liuyh
 * @Date 2021/5/12
 * @Version V0.0.1
 */
@Mapper
public interface DatasourceMapper extends BaseMapper<PlDataSource> {
}
