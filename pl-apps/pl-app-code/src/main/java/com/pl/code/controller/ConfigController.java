package com.pl.code.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pl.code.config.DefaultGenerateConfig;
import com.pl.code.entity.bo.PlGenerateConfigBO;
import com.pl.code.entity.dto.config.AddGenerateConfigDTO;
import com.pl.code.entity.dto.config.UpdateGenerateConfigDTO;
import com.pl.code.entity.po.PlGenerateConfig;
import com.pl.code.entity.vo.PlGenerateConfogVO;
import com.pl.code.service.PlGenerateConfigService;
import com.pl.core.annotation.PlRestController;
import com.pl.core.exception.BusinessException;
import com.pl.data.core.entity.PageDTO;
import com.pl.data.core.response.Result;
import com.pl.data.core.utils.PoJoConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @ClasssName GenerateConfigController
 * @Description 配置相关API
 * @Author Liuyh
 * @Date 2021/5/15
 * @Version V0.0.1
 */
@PlRestController("config")
@Validated
public class ConfigController {
    @Resource
    private DefaultGenerateConfig generateConfig;
    @Resource
    private PlGenerateConfigService plGenerateConfigService;

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Long id) {
        PlGenerateConfogVO confogVO = new PlGenerateConfogVO();
        if (id.equals(-1L)) {
            confogVO.setId(-1L);
            confogVO.setName("默认配置");
            try {

                PlGenerateConfigBO configBO = PlGenerateConfigBO.parse(generateConfig);
                confogVO.setConfig(configBO);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            PlGenerateConfig config = plGenerateConfigService.getById(id);
            confogVO = config.po2vo();
        }
        return Result.ok(confogVO);
    }

    /**
     * 新增
     *
     * @param configDTO
     * @return
     */
    @PutMapping("save")
    public Result save(@Validated @RequestBody AddGenerateConfigDTO configDTO) {
        PlGenerateConfig config = configDTO.dto2po();
        boolean state = plGenerateConfigService.save(config);
        if (!state) {
            throw new BusinessException("新增失败");
        }
        return Result.ok(config.getId());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public Result delete(@Validated @NotNull(message = "id不能为空") @PathVariable("id") Long id) {
        return Result.ok(plGenerateConfigService.removeById(id));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("delete/batch")
    public Result delete(@Validated @NotEmpty(message = "id不能为空") @RequestParam(value = "ids") Long[] ids) {
        return Result.ok(plGenerateConfigService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 修改
     *
     * @param configDTO
     * @return
     */
    @PostMapping("update")
    public Result update(@Validated @RequestBody UpdateGenerateConfigDTO configDTO) {
        PlGenerateConfig config = configDTO.dto2po();
        return Result.ok(plGenerateConfigService.updateById(config));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param keywords
     * @return
     */

    @GetMapping("/page")
    public Result page(PageDTO page, String keywords) {
        LambdaQueryWrapper<PlGenerateConfig> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keywords)) {
            queryWrapper.like(PlGenerateConfig::getName, keywords);
            queryWrapper.like(PlGenerateConfig::getConfigContent, keywords);
        }
        IPage pageData = plGenerateConfigService.page(page.toPage(), queryWrapper);
        return Result.ok(PoJoConverter.po2vo(pageData));
    }


    @GetMapping("/list")
    public Result list(String keywords) {
        LambdaQueryWrapper<PlGenerateConfig> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keywords)) {
            queryWrapper.like(PlGenerateConfig::getName, keywords);
            queryWrapper.like(PlGenerateConfig::getConfigContent, keywords);
        }
        List<PlGenerateConfig> configList = plGenerateConfigService.list(queryWrapper);
        return Result.ok(PoJoConverter.po2vo(configList));
    }
}
