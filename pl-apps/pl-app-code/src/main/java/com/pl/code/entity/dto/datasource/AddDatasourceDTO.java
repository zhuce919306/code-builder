package com.pl.code.entity.dto.datasource;

import com.pl.core.utils.AssertUtil;
import com.pl.datasource.dynamic.constants.ConnectionTypeEnum;
import com.pl.datasource.dynamic.constants.UrlPatternEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClasssName AddDatasourceDTO
 * @Description 新增数据源DTO
 * @Author liuds
 * @Date 2021/5/13
 * @Version V0.0.1
 */
@Data
public class AddDatasourceDTO {

    /**
     * 名称
     */
    @NotBlank(message = "数据源名称不能为空")
    private String name;

    /**
     * 连接类型 0:主机连接,1:URl连接
     */
    @NotNull(message = "连接类型不能为空")
    private Integer connectionType;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

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
     * URL
     */
    private String url;

    /**
     * 验证参数
     */
    public void valid() {
        // 如果是主机连接
        if (ConnectionTypeEnum.HOST.typeEquals(connectionType)) {
            AssertUtil.notEmpty(host, "主机地址不能为空");
            AssertUtil.notEmpty(host, "端口不能为空");
            AssertUtil.notEmpty(dbName, "数据库名称不能为空");
        } else {
            AssertUtil.notEmpty(url, "连接地址不能为空");
        }
        AssertUtil.notEmpty(dbType,"数据库类型不能为空");
        AssertUtil.notNull(UrlPatternEnum.get(dbType), "未知数据库类型");

    }
}