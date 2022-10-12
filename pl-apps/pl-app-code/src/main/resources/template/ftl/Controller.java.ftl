<#assign pagePkg=""/>
<#assign pageClsName=""/>
<#if style == 'pl-web'>
    <#assign pageClsName= "AbstractPage"/>
    <#assign pagePkg= "import com.pl.data.core.entity.dto.AbstractPage;"/>
<#elseif style == 'mybatis-plus'>
    <#assign pageClsName= "Page"/>
    <#assign pagePkg= "import com.baomidou.mybatisplus.extension.plugins.pagination.Page;"/>
</#if>
package ${packageName}.controller;

<#if model.wired>
import ${packageName}.service.${model.firstUpperCaseName}Service;
</#if>
<#if swagger>
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
</#if>
<#if model.basicApi>
import ${model.responsePackageName};
${pagePkg}
</#if>
<#if model.importPackages??>
    <#list model.importPackages as pkg>
import ${pkg};
    </#list>
</#if>
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
* ${model.comment}Controller
* @author ${author}
* @date ${date}
* @version V0.0.1
*/
<#--<#if model.annotations??>-->

    <#--<#list model.annotations as annotation>-->
<#--${annotation.name}${vmTools.buildAnnotayionProperties(annotation.properties)}-->
    <#--</#list>-->
<#--</#if>-->
@Slf4j
@RestController
@RequestMapping("/v1/${path}")
@Api(value = "${path}", tags = "${model.comment}API接口")
public class ${model.firstUpperCaseName}Controller {
<#if model.wired>
    <#if model.wiredType == 'resource'>

    @Resource
    private ${model.firstUpperCaseName}Service ${model.firstLowerCaseName}Service;
    </#if>
    <#if model.wiredType == 'autoWired'>

    @Autowired
    private ${model.firstUpperCaseName}Service ${model.firstLowerCaseName}Service;
    </#if>
    <#if model.wiredType == 'constructor'>

    private final ${model.firstUpperCaseName}Service ${model.firstLowerCaseName}Service;

    public ${model.firstUpperCaseName}Controller(${model.firstUpperCaseName}Service ${model.firstLowerCaseName}Service) {
        this.${model.firstLowerCaseName}Service = ${model.firstLowerCaseName}Service;
    }
    </#if>
    <#if model.basicApi && style != 'mybatis'>

    /**
     * 列表查询
     * @return
     */
        <#if swagger>
    @ApiOperation(value = "${model.comment}列表查询", notes = "${model.comment}列表查询")
        </#if>
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public ${model.responseClassName}<List<${model.firstUpperCaseName}>> getList(@RequestBody @Valid ${model.firstUpperCaseName}Dto listQuery) {
        return ${model.responseClassName}.${model.successMethod}(${model.firstLowerCaseName}Service.list(listQuery));
    }

    /**
     * 分页查询
     * @return
     */
    <#if swagger>
    @ApiOperation(value = "${model.comment}分页查询", notes = "${model.comment}分页查询")
    </#if>
    @RequestMapping(value = "/page", method = {RequestMethod.POST})
    public ${model.responseClassName}<${pageClsName}<${model.firstUpperCaseName}>> getPage(@RequestBody @Valid ${model.firstUpperCaseName}Dto listQuery) {
        return ${model.responseClassName}.${model.successMethod}(${model.firstLowerCaseName}Service.page(listQuery));
    }

    /**
     * 新增
     *
     * @param ${model.firstLowerCaseName} ${model.comment}
     * @return
     */
    <#if swagger>
    @ApiOperation(value = "${model.comment}新增", notes = "${model.comment}新增", httpMethod = "POST")
    </#if>
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @LogOperation("${model.comment}新增")
    @Idempotent(isIdempotent = true,expireTime = 4,timeUnit = TimeUnit.SECONDS,info = "请勿重复点击",delKey = true)
    public ${model.responseClassName} save(@RequestBody @Valid ${model.firstUpperCaseName} ${model.firstLowerCaseName}) {
        ${model.firstLowerCaseName}Service.save(${model.firstLowerCaseName});
        return ${model.responseClassName}.${model.successMethod}();
    }


    /**
     * 修改${model.comment}
     *
     * @param ${model.firstLowerCaseName} ${model.comment}
     * @return
     */
    <#if swagger>
    @ApiOperation(value = "修改${model.comment}", notes = "修改${model.comment}", httpMethod = "POST")
    </#if>
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @LogOperation("${model.comment}修改")
    @Idempotent(isIdempotent = true,expireTime = 4,timeUnit = TimeUnit.SECONDS,info = "请勿重复点击",delKey = true)
    public ${model.responseClassName} update(@RequestBody @Valid ${model.firstUpperCaseName} ${model.firstLowerCaseName}) {
        ${model.firstLowerCaseName}Service.updateById(${model.firstLowerCaseName});
        return  ${model.responseClassName}.${model.successMethod}();
    }

    /**
     * 通过id查询${model.comment}
     * @param ${model.pkName} id
     * @return
     */
    <#if swagger>
    @ApiOperation(value = "通过id查询${model.comment}", notes = "通过id查询${model.comment}", httpMethod = "GET")
    </#if>
    @RequestMapping(value = "/findById", method = {RequestMethod.GET})
    public ${model.responseClassName}<${model.firstUpperCaseName}> findById(${model.pkJavaType} ${model.pkName}) {
        return ${model.responseClassName}.${model.successMethod}(${model.firstLowerCaseName}Service.findById(id));
    }

    /**
     * ${model.comment}数据导出
     *
     * @param response
     * @return
     */
        <#if swagger>
    @ApiOperation(value = "${model.comment}数据导出", notes = "${model.comment}数据导出", httpMethod = "POST")
        </#if>
    @RequestMapping(value = "/exportExcel", method = {RequestMethod.POST})
    @Idempotent(isIdempotent = true,expireTime = 4,timeUnit = TimeUnit.SECONDS,info = "请勿重复点击",delKey = true)
    public void exportExcel(@RequestBody ${model.firstUpperCaseName}Dto listQuery, HttpServletResponse response) throws IOException {
        List<${model.firstUpperCaseName}> list = ${model.firstLowerCaseName}Service.list(listQuery);
        ExcelUtils.exportExcel(list, "标题", "${model.comment}", ${model.firstUpperCaseName}.class, "${model.comment}", response);
    }

    /**
     * ${model.comment}下载模板
     *
     * @param response
     * @return
     */
        <#if swagger>
    @ApiOperation(value = "${model.comment}下载模板", notes = "${model.comment}下载模板", httpMethod = "POST")
        </#if>
    @RequestMapping(value = "/excelTemplate", method = {RequestMethod.POST})
    @Idempotent(isIdempotent = true,expireTime = 4,timeUnit = TimeUnit.SECONDS,info = "请勿重复点击",delKey = true)
    public void excelTemplate(HttpServletResponse response) throws Exception {
        String filePath = "template/template.xlsx";
        TemplateExportParams templatePath = new TemplateExportParams(filePath);
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("date", sdf.format(new Date()));
        List<${model.firstUpperCaseName}> list = new ArrayList<>();
        <#--SysLogOperationEntity sysLogOperationEntity = new SysLogOperationEntity();-->
        <#--sysLogOperationEntity.setOperation("用户操作");-->
        <#--sysLogOperationEntity.setType("操作日志");-->
        <#--sysLogOperationEntity.setRequestUri("url");-->
        <#--sysLogOperationEntity.setRequestMethod("请求方式");-->
        <#--list.add(sysLogOperationEntity);-->
        map.put("sysList", list);
        ExcelUtils.exportExcel(templatePath, map, "表名", response);
    }

    /**
     * ${model.comment}数据导入
     *
     * @param file
     * @return
     */
        <#if swagger>
    @ApiOperation(value = "${model.comment}数据导入", notes = "${model.comment}数据导入", httpMethod = "POST")
        </#if>
    @RequestMapping(value = "/importExcel", method = {RequestMethod.POST})
    @Idempotent(isIdempotent = true,expireTime = 4,timeUnit = TimeUnit.SECONDS,info = "请勿重复点击",delKey = true)
    public ${model.responseClassName} importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<${model.firstUpperCaseName}> list = ExcelUtils.importExcel(file, ${model.firstUpperCaseName}.class);
        list.forEach(${model.firstLowerCaseName} -> {
            ${model.firstLowerCaseName}Service.save(${model.firstLowerCaseName});
        });
        return ${model.responseClassName}.${model.successMethod}();
    }

    /**
     * ${model.comment}数据根据模板导出
     *
     * @param response
     * @return
     */
        <#if swagger>
    @ApiOperation(value = "${model.comment}数据根据模板导出", notes = "${model.comment}数据根据模板导出", httpMethod = "POST")
        </#if>
    @RequestMapping(value = "/excelTemplateExport", method = {RequestMethod.POST})
    @Idempotent(isIdempotent = true,expireTime = 4,timeUnit = TimeUnit.SECONDS,info = "请勿重复点击",delKey = true)
    public void excelTemplateExport(@RequestBody ${model.firstUpperCaseName}Dto listQuery, HttpServletResponse response) throws IOException {
        String filePath = "template/template.xlsx";
        //TemplateExportParams templatePath = new TemplateExportParams("E:/hh/template.xlsx");
        TemplateExportParams templatePath = new TemplateExportParams(filePath);
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("date", sdf.format(new Date()));
        List<${model.firstUpperCaseName}> list = ${model.firstLowerCaseName}Service.list(listQuery);
        map.put("sysList", list);
        ExcelUtils.exportExcel(templatePath, map, "表名", response);
    }

    /**
     * 测试定时任务
     * @return void
    */
     public void task() {
        //注意：在cron.setting表里添加相关内容即可使用
    }

    </#if>
</#if>

}
