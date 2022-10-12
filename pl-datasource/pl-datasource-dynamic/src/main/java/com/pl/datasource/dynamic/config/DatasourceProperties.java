package com.pl.datasource.dynamic.config;

import com.pl.datasource.dynamic.constants.DataSourceConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @ClasssName DatasourceProperties
 * @Description 数据源配置文件
 * @Author Liuyh
 * @Date 2021/7/8
 * @Version V0.0.1
 */
@Data
@Component("datasourceProperties")
@ConfigurationProperties("spring.datasource.druid")
@Primary
public class DatasourceProperties {
    /**
     * 是否加密
     */
    private boolean encrypt;
    /**
     * 默认数据源
     */
    private String defaultDatasource = DataSourceConstants.DEFAULT_DS_MASTER;
    /**
     * 数据源用户名
     */
    private String username;

    /**
     * 数据源密码
     */
    private String password;

    /**
     * jdbcurl
     */
    private String url;

    /**
     * 数据源驱动
     */
    private String driverClassName;

    /**
     * 查询数据源的SQL
     */
    private String querySql = "select * from pl_datasource where delete_flag = 0";

    /**
     * 动态数据源列名配置
     */
    private Column column;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Column {

        /**
         * 连接类型
         */
        private String connectionType = DataSourceConstants.COLUMN_DS_CONNECTION_TYPE;
        /**
         * 数据源名称
         */
        private String dsName = DataSourceConstants.COLUMN_DS_NAME;
        /**
         * 数据库类型
         */
        private String type = DataSourceConstants.COLUMN_DS_DB_TYPE;
        /**
         * 连接地址
         */
        private String url = DataSourceConstants.COLUMN_DS_URL;
        /**
         * 主机
         */
        private String host = DataSourceConstants.COLUMN_DS_HOST;
        /**
         * 端口
         */
        private String port = DataSourceConstants.COLUMN_DS_PORT;
        /**
         * 实例名称
         */
        private String instance = DataSourceConstants.COLUMN_DS_DB_INSTANCE;
        /**
         * 数据库
         */
        private String dbName = DataSourceConstants.COLUMN_DS_DB_NAME;
        /**
         * 用户名
         */
        private String username = DataSourceConstants.COLUMN_DS_USERNAME;
        /**
         * 密码
         */
        private String password = DataSourceConstants.COLUMN_DS_PASSWORD;
    }
}
