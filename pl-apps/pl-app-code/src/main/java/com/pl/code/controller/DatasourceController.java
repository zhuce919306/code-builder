package com.pl.code.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pl.code.entity.dto.datasource.AddDatasourceDTO;
import com.pl.code.entity.dto.datasource.UpdateDatasourceDTO;
import com.pl.code.entity.po.PlDataSource;
import com.pl.code.service.DatasourceService;
import com.pl.core.annotation.PlRestController;
import com.pl.data.core.entity.PageDTO;
import com.pl.data.core.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClasssName DatasourceController
 * @Description 数据源Ctroller
 * @Author Liuyh
 * @Date 2021/5/12
 * @Version V0.0.1
 */
@PlRestController("datasource")
@Validated
public class DatasourceController {

    @Resource
    private DatasourceService datasourceService;

    /**
     * 新增数据源
     *
     * @param addDatasourceDTO
     * @return
     */
    @PutMapping("add")
    public Result add(AddDatasourceDTO addDatasourceDTO) {
        addDatasourceDTO.valid();
        PlDataSource dataSource = new PlDataSource();
        BeanUtil.copyProperties(addDatasourceDTO, dataSource);
        return Result.ok(datasourceService.saveDatasource(dataSource));
    }

    /**
     * 删除数据源
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public Result delete(@Validated @NotNull(message = "数据源id不能为空") @PathVariable("id") Long id) {
        return Result.ok(datasourceService.removeDatasource(id));
    }

    /**
     * 批量删除数据源
     *
     * @param ids
     * @return
     */
    @DeleteMapping("delete/batch")
    public Result delete(@Validated @NotEmpty(message = "数据源id不能为空") @RequestParam(value = "ids") Long[] ids) {
        return Result.ok(datasourceService.removeDatasource(ids));
    }

    /**
     * 修改数据源
     *
     * @param updateDatasourceDTO
     * @return
     */
    @PostMapping("update")
    public Result update(@Validated UpdateDatasourceDTO updateDatasourceDTO) {
        updateDatasourceDTO.valid();
        PlDataSource dataSource = new PlDataSource();
        BeanUtil.copyProperties(updateDatasourceDTO, dataSource);
        return Result.ok(datasourceService.updateDatasource(dataSource));
    }

    /**
     * 分页查询数据源
     *
     * @param page
     * @param keywords
     * @return
     */

    @GetMapping("/page")
    public Result page(PageDTO page, String keywords) {
        LambdaQueryWrapper<PlDataSource> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keywords)) {
            queryWrapper.like(PlDataSource::getName, keywords);
            queryWrapper.like(PlDataSource::getUrl, keywords);
        }
        return Result.ok(datasourceService.page(page.toPage(), queryWrapper));
    }

    /**
     * 获取数据源列表
     *
     * @param keywords
     * @return
     */
    @GetMapping("/list")
    public Result list(String keywords) {
        LambdaQueryWrapper<PlDataSource> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keywords)) {
            queryWrapper.like(PlDataSource::getName, keywords);
            queryWrapper.like(PlDataSource::getUrl, keywords);
        }
        return Result.ok(datasourceService.list(queryWrapper));
    }

    /**
     * 数据源详情
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result info(@Validated @NotNull(message = "数据源id不能为空") @PathVariable("id") Long id) {
        return Result.ok(datasourceService.getById(id));
    }
}
