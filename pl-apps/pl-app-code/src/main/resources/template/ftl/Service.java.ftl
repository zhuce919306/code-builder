<#assign superClass=""/>
<#assign superGeneric=""/>
<#if model.extendsSupperClass>
    <#assign superClass= "extends " + model.superClassName/>
    <#assign superGeneric= vmTools.buildGeneric(model.superGenericList)/>
</#if>
package ${packageName}.service;

<#if model.importPackages??>
    <#list model.importPackages as pkg>
import ${pkg};
    </#list>

</#if>
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * ${model.comment}Service
 * @author ${author}
 * @date ${date}
 * @version V0.0.1
 */
public interface ${model.firstUpperCaseName}Service {


     List<${model.firstUpperCaseName}> list(${model.firstUpperCaseName}Dto listQuery);

     Page<${model.firstUpperCaseName}> page(${model.firstUpperCaseName}Dto listQuery);

     void save(${model.firstUpperCaseName} ${model.firstLowerCaseName});

     void updateById(${model.firstUpperCaseName} ${model.firstLowerCaseName});

     ${model.firstUpperCaseName} findById(Long id);

}
