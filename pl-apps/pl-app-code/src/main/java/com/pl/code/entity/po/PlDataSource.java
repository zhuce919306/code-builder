package com.pl.code.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pl.data.core.entity.AbstractPlEntity;
import com.pl.datasource.dynamic.constants.ConnectionTypeEnum;
import com.pl.datasource.dynamic.constants.UrlPatternEnum;
import lombok.Data;

/**
 * @ClasssName PlDataSource
 * @Description 数据源
 * @Author Liuyh
 * @Date 2021/5/12
 * @Version V0.0.1
 */
@Data
@TableName("pl_datasource")
public class PlDataSource extends AbstractPlEntity<PlDataSource> {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 连接类型 0:主机连接,1:URl连接
     */
    private Integer connectionType;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * 实例名
     */
    private String instance;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public String getUrl() {
        String url = this.url;
        if (ConnectionTypeEnum.HOST.typeEquals(this.getConnectionType())){
            UrlPatternEnum urlPattern = UrlPatternEnum.get(this.getDbType());
            url = urlPattern.of(this.getHost(), this.getPort(), this.getDbName());
        }
        return url;
    }
}
