package com.pl.code.core;

import cn.hutool.core.collection.CollectionUtil;
import com.pl.code.config.DefaultGenerateConfig;
import com.pl.code.config.properties.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClasssName ConfigAdapter
 * @Description
 * @Author liuds
 * @Date 2021/5/29
 * @Version V0.0.1
 */
@Component
public class ConfigAdapter {
    @Resource
    private DefaultGenerateConfig defaultGenerateConfig;

    /**
     * 获取实体生成配置
     * @param config
     * @return
     */
    public EntityConfigProperties getEntityConfig(GenerateConfig config){
        EntityConfigProperties entityConfig = config.getEntityConfig();
        if (entityConfig == null){
            entityConfig = defaultGenerateConfig.getEntityConfig();
        }
        return entityConfig;
    }

    /**
     * 获取lombok配置
     * @param config
     * @return
     */
    public LombokConfigProperties getLombokConfig(GenerateConfig config){
        LombokConfigProperties lombokConfig = config.getEntityConfig().getLombokConfig();
        if (lombokConfig == null){
            lombokConfig = defaultGenerateConfig.getEntityConfig().getLombokConfig();
        }
        return lombokConfig;
    }

    /**
     * 获取列名配置
     * @param config
     * @return
     */
    public ColumnConfigProperties getColumnConfig(GenerateConfig config){
        ColumnConfigProperties columnConfig = config.getColumnConfig();
        if (columnConfig == null){
            columnConfig = defaultGenerateConfig.getColumnConfig();
        }
        return columnConfig;
    }

    /**
     * 获取类型映射配置
     * @param config
     * @return
     */
    public List<TypeMapperConfigProperties> getTypeMapperConfig(GenerateConfig config){
        List<TypeMapperConfigProperties> typeMapperConfig = config.getTypeMapperConfig();
        if (CollectionUtil.isEmpty(typeMapperConfig)){
            typeMapperConfig = defaultGenerateConfig.getTypeMapperConfig();
        }
        return typeMapperConfig;
    }

    /**
     * 获取mapper配置
     * @param config
     * @return
     */
    public MapperConfigProperties getMapperConfig(GenerateConfig config){
        MapperConfigProperties mapperConfig = config.getMapperConfig();
        if (mapperConfig == null){
            mapperConfig = defaultGenerateConfig.getMapperConfig();
        }
        return mapperConfig;
    }

}
