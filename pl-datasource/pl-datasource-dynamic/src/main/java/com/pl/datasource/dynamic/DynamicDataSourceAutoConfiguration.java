package com.pl.datasource.dynamic;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.pl.datasource.dynamic.config.DatasourceProperties;
import com.pl.datasource.dynamic.core.DatasourceProcessor;
import com.pl.datasource.dynamic.core.JdbcDynamicDataSourceProvider;
import com.pl.datasource.dynamic.core.WebMvcConfig;
import lombok.AllArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClasssName DynamicDataSourceAutoConfiguration
 * @Description
 * @Author Liuyh
 * @Date 2021/7/8
 * @Version V0.0.1
 */
@Configuration
@AllArgsConstructor
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DatasourceProperties.class)
public class DynamicDataSourceAutoConfiguration {


    private final StringEncryptor stringEncryptor;

    private final DatasourceProperties properties;

    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new JdbcDynamicDataSourceProvider(stringEncryptor, properties);
    }

    @Bean
    public DsProcessor dsProcessor() {
        return new DatasourceProcessor();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfig();
    }

}
