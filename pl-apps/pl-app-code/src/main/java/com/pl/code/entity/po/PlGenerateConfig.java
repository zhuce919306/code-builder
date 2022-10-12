package com.pl.code.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pl.code.entity.bo.PlGenerateConfigBO;
import com.pl.code.entity.vo.PlGenerateConfogVO;
import com.pl.data.core.entity.AbstractPlEntity;
import lombok.Data;

/**
 * @ClasssName PlGenerateConfig
 * @Description 生成配置
 * @Author Liuyh
 * @Date 2021/5/15
 * @Version V0.0.1
 */
@Data
@TableName("pl_generate_config")
public class PlGenerateConfig extends AbstractPlEntity<PlGenerateConfig> {
    @TableId(type = IdType.AUTO, value = "id")
    private Long id;
    private String name;
    private String configContent;


    /**
     * po 转 vo
     *
     * @return
     */
    public PlGenerateConfogVO po2vo() {
        PlGenerateConfogVO vo = new PlGenerateConfogVO();
        vo.setId(this.id);
        vo.setName(this.name);
        vo.setCreateTime(this.getCreateTime());
        vo.setUpdateTime(this.getUpdateTime());
        vo.setConfig(PlGenerateConfigBO.parse(this.configContent));
        return vo;
    }

}