<#assign superClass=""/>
<#assign superGeneric=""/>
<#if model.extendsSupperClass>
    <#assign superClass= "extends " + model.superClassName/>
    <#assign superGeneric= vmTools.buildGeneric(model.superGenericList)/>
</#if>
package ${packageName}.mapper;

<#if model.importPackages??>
    <#list model.importPackages as pkg>
import ${pkg};
    </#list>

</#if>
/**
 * ${model.comment}Mapper
 * @author ${author}
 * @date ${date}
 * @version V0.0.1
 */
<#if model.annotations??>

    <#list model.annotations as annotation>
${annotation.name}${vmTools.buildAnnotayionProperties(annotation.properties)}
    </#list>
</#if>
public interface ${model.firstUpperCaseName}Mapper ${superClass}${superGeneric} {
}
