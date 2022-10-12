package com.pl.code.controller;

import cn.hutool.core.io.IoUtil;
import com.pl.code.core.FreemarkerUtil;
import com.pl.code.core.GenerateConfig;
import com.pl.code.entity.dto.generate.GenerateDTO;
import com.pl.code.entity.po.PlGenerateConfig;
import com.pl.code.service.PlGenerateConfigService;
import com.pl.core.annotation.PlRestController;
import com.pl.core.exception.BusinessException;
import com.pl.data.core.response.Result;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClasssName GenerateController
 * @Description
 * @Author liuds
 * @Date 2021/5/17
 * @Version V0.0.1
 */
@PlRestController("generate")
@Validated
public class GenerateController {
    @Resource
    private FreemarkerUtil freemarkerUtil;
    @Resource
    private PlGenerateConfigService plGenerateConfigService;

    @PostMapping("/show")
    public Result generate(@RequestBody GenerateDTO dto) {
        GenerateConfig config = buildConfig(dto);
        return Result.ok(freemarkerUtil.generate(dto.getTemplates(), config));
    }

    @SneakyThrows
    @PostMapping("download")
    public void download(@RequestBody GenerateDTO dto, HttpServletResponse response){
        GenerateConfig config = buildConfig(dto);
        byte [] data = freemarkerUtil.download(dto.getTemplates(), config);
        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=%s.zip", config.getTableName()));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
    }

    /**
     * 生成配置
     * @param dto
     * @return
     */
    private GenerateConfig buildConfig(GenerateDTO dto){
        PlGenerateConfig configPO = plGenerateConfigService.getById(dto.getConfigId());
        if (configPO == null) {
            throw new BusinessException("未找到配置");
        }
        GenerateConfig config = GenerateConfig.parse(dto, configPO);
        return config;
    }
}
