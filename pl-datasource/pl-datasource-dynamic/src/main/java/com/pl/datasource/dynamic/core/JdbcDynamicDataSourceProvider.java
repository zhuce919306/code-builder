package com.pl.datasource.dynamic.core;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.pl.datasource.dynamic.config.DatasourceProperties;
import com.pl.datasource.dynamic.constants.ConnectionTypeEnum;
import com.pl.datasource.dynamic.constants.DataSourceConstants;
import com.pl.datasource.dynamic.constants.UrlPatternEnum;
import org.jasypt.encryption.StringEncryptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClasssName JdbcDynamicDataSourceProvider
 * @Description JDBC动态数据源提供者(从数据库中获取数据源)
 * @Author liuds
 * @Date 2021/7/8
 * @Version V0.0.1
 */
public class JdbcDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {

    private final StringEncryptor stringEncryptor;

    private final DatasourceProperties properties;

    public JdbcDynamicDataSourceProvider(StringEncryptor stringEncryptor, DatasourceProperties properties) {
        super(properties.getDriverClassName(), properties.getUrl(), properties.getUsername(), properties.getPassword());
        this.stringEncryptor = stringEncryptor;
        this.properties = properties;
    }

    /**
     * 执行语句获得数据源参数
     *
     * @param statement 语句
     * @return 数据源参数
     * @throws SQLException sql异常
     */
    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
        String querySql = properties.getQuerySql();
        Map<String, DataSourceProperty> map = new HashMap<>(8);
        ResultSet result = statement.executeQuery(querySql);
        DatasourceProperties.Column column = getColumnConfig();
        while (result.next()) {
            String name = result.getString(getColumnName(column.getDsName(), DataSourceConstants.COLUMN_DS_NAME));
            String username = result.getString(getColumnName(column.getUsername(), DataSourceConstants.COLUMN_DS_USERNAME));
            String password = result.getString(getColumnName(column.getPassword(), DataSourceConstants.COLUMN_DS_PASSWORD));
            String dbType = result.getString(getColumnName(column.getType(), DataSourceConstants.COLUMN_DS_DB_TYPE));
            Integer connectionType = result.getInt(getColumnName(column.getConnectionType(), DataSourceConstants.COLUMN_DS_PASSWORD));
            boolean encrypt = properties.isEncrypt();
            String url = null;
            if (ConnectionTypeEnum.URL.typeEquals(connectionType)) {
                url = result.getString(getColumnName(column.getUrl(), DataSourceConstants.COLUMN_DS_URL));
            } else {
                String host = result.getString(getColumnName(column.getHost(), DataSourceConstants.COLUMN_DS_HOST));
                String port = result.getString(getColumnName(column.getPort(), DataSourceConstants.COLUMN_DS_PORT));
                String dbName = result.getString(getColumnName(column.getDbName(), DataSourceConstants.COLUMN_DS_DB_NAME));
                if (UrlPatternEnum.SQLSERVER.nameEquals(dbType)) {
                    String instance = result.getString(getColumnName(column.getInstance(), DataSourceConstants.COLUMN_DS_DB_INSTANCE));
                    url = UrlPatternEnum.SQLSERVER.of(host, instance, port, dbName);
                } else {
                    UrlPatternEnum urlPattern = UrlPatternEnum.get(dbType);
                    url = urlPattern.of(host, port, dbName);
                }
            }
            password = encrypt ? stringEncryptor.decrypt(password) : password;
            DataSourceProperty property = new DataSourceProperty();
            property.setUsername(username);
            property.setPassword(password);
            property.setUrl(url);
            map.put(name, property);
        }

        // 添加默认主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setUsername(properties.getUsername());
        property.setPassword(properties.getPassword());
        property.setUrl(properties.getUrl());
        map.put(properties.getDefaultDatasource(), property);
        return map;
    }


    /**
     * 获取列名
     *
     * @param column
     * @param defaultColumn
     * @return
     */
    private String getColumnName(String column, String defaultColumn) {
        return StrUtil.isNotBlank(column) ? column : defaultColumn;
    }


    /**
     * 获取列名配置
     *
     * @return
     */
    private DatasourceProperties.Column getColumnConfig() {
        DatasourceProperties.Column column = properties.getColumn();
        if (column == null) {
            column = new DatasourceProperties.Column();
        }
        return column;
    }
}
