package com.pl.code.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pl.code.entity.po.PlGenerateConfig;
import com.pl.code.mapper.PlGenerateConfigMapper;
import com.pl.code.service.PlGenerateConfigService;
import org.springframework.stereotype.Service;

/**
 * @ClasssName PlGenerateConfigServiceImpl
 * @Description 生成配置Service实现
 * @Author Liuyh
 * @Date 2021/5/15
 * @Version V0.0.1
 */
@Service
public class PlGenerateConfigServiceImpl extends ServiceImpl<PlGenerateConfigMapper, PlGenerateConfig> implements PlGenerateConfigService {
}
