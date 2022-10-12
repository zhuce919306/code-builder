package com.pl;

import com.pl.datasource.dynamic.annotation.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClasssName PlCodeGeneraterApplication
 * @Description 代码生成服务
 * @Author Liuyh
 * @Date 2021/7/30
 * @Version V0.0.1
 */
@EnableDynamicDataSource
@SpringBootApplication
public class PlCodeGeneraterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlCodeGeneraterApplication.class, args);
    }
}
