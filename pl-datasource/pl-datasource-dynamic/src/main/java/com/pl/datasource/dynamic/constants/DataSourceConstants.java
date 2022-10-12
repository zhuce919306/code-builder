package com.pl.datasource.dynamic.constants;

/**
 * @ClasssName DataSourceConstants
 * @Description 数据源常量
 * @Author liuds
 * @Date 2021/7/8
 * @Version V0.0.1
 */
public interface DataSourceConstants {
    /**
     * 默认数据源（master）
     */
    String DEFAULT_DS_MASTER = "master";

    /**
     * 数据源名称列名
     */
    String COLUMN_DS_NAME = "name";

    /**
     * 连接类型列名
     */
    String COLUMN_DS_CONNECTION_TYPE = "connection_type";

    /**
     * 数据库类型列名
     */
    String COLUMN_DS_DB_TYPE = "db_type";

    /**
     * 连接地址列名
     */
    String COLUMN_DS_URL = "url";

    /**
     * 主机列名
     */
    String COLUMN_DS_HOST = "host";

    /**
     * 端口列名
     */
    String COLUMN_DS_PORT = "port";

    /**
     * 实例列名(sqlserver)
     */
    String COLUMN_DS_DB_INSTANCE = "instance";

    /**
     * 数据库名称列名
     */
    String COLUMN_DS_DB_NAME = "db_name";

    /**
     * 用户名列名
     */
    String COLUMN_DS_USERNAME = "username";

    /**
     * 密码列名
     */
    String COLUMN_DS_PASSWORD = "password";
}
