package com.pl.code.controller;

import com.pl.code.service.CodeGenerateService;
import com.pl.core.annotation.PlRestController;
import com.pl.data.core.entity.PageDTO;
import com.pl.data.core.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @ClasssName TableController
 * @Description 表Controller
 * @Author liuds
 * @Date 2021/5/13
 * @Version V0.0.1
 */
@PlRestController("table")
@Validated
public class TableController {
    @Resource
    private CodeGenerateService codeGenerateService;

    /**
     * 分页查询表
     * @param page
     * @param dsName
     * @param tableName
     * @return
     */
    @GetMapping("page")
    public Result page(PageDTO page, @NotBlank(message = "数据源不能为空") String dsName, String tableName) {
        return Result.ok(codeGenerateService.getPage(page.toPage(), tableName,dsName));
    }
}
