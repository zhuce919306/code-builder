package com.pl.code.config;

import com.pl.code.config.properties.PlGenerateProperties;
import com.pl.code.core.YamlPropertyResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClasssName DefaultGenerateConfig
 * @Description 代码生成配置
 * @Author liuds
 * @Date 2021/5/15
 * @Version V0.0.1
 */

@Component
@ConfigurationProperties(prefix = "pl-generate")
@PropertySource(value = "classpath:pl-generator.yml", factory = YamlPropertyResourceFactory.class)
public class DefaultGenerateConfig extends PlGenerateProperties {

}