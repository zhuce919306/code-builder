package com.pl.code.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pl.code.config.DefaultGenerateConfig;
import com.pl.code.entity.bo.PlGenerateConfigBO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClasssName PageGenerateConfogVO
 * @Description 生成配置分页VO
 * @Author liuds
 * @Date 2021/5/15
 * @Version V0.0.1
 */
@Data
public class PlGenerateConfogVO {


    private Long id;

    private String name;

    private PlGenerateConfigBO config;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    public void configToVo(DefaultGenerateConfig generateConfig) {
        this.config = PlGenerateConfigBO.parse(generateConfig);
    }


}