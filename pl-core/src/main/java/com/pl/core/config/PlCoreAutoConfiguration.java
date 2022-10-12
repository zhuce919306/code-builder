package com.pl.core.config;

import com.pl.core.utils.ApplicationContextUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @ClasssName PlCoreAutoConfiguration
 * @Description
 * @Author Liuyh
 * @Date 2021/7/30
 * @Version V0.0.1
 */

@Configurable
public class PlCoreAutoConfiguration {
    @Bean("applicationContextUtils")
    public ApplicationContextUtils applicationContextUtils(){
        return new ApplicationContextUtils();
    }
}
