package com.pl.data.core.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Optional;

/**
 * @ClasssName PageDTO
 * @Description 分页参数抽象类
 * @Author Liuyh
 * @Date 2021/7/6
 * @Version V0.0.1
 */
@Data
public class PageDTO {

    private Long pageSize;

    private Long pageNo;


    /**
     * 转换成分页对象
     *
     * @return
     */
    public Page toPage() {
        return new Page(Optional.ofNullable(pageNo).orElse(1L), Optional.ofNullable(pageSize).orElse(10L));
    }

}
