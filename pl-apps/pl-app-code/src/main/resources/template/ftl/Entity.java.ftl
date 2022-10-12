<#assign superClass=""/>
<#assign superGeneric=""/>
<#if model.extendsSupperClass>
    <#assign superClass= "extends " + model.superClassName/>
    <#assign superGeneric= vmTools.buildGeneric(model.superGenericList)/>
</#if>
package ${packageName}.entity;

<#if model.importPackages??>
    <#list model.importPackages as pkg>
import ${pkg};
    </#list>

</#if>
import com.baomidou.mybatisplus.annotation.TableField;
/**
* ${model.comment}
* @author ${author}
* @date ${date}
* @version V0.0.1
*/
<#if model.annotations??>

    <#list model.annotations as annotation>
${annotation.name}${vmTools.buildAnnotayionProperties(annotation.properties)}
    </#list>
</#if>
public class ${model.firstUpperCaseName} extends BaseEntity{
<#list model.fieldList as field>
    <#if !field.hidden>

        <#if field.comment?length gt 1>
    /**
     * ${field.comment}
     */
        </#if>
    <#if field.annotations??>
        <#list field.annotations as annotation>
    ${annotation.name}${vmTools.buildAnnotayionProperties(annotation.properties)}
        </#list>
    </#if>
    private ${field.type} ${field.firstLowerCaseName};
    </#if>
</#list>

    @TableField(exist = false)
    @ApiModelProperty(value = "页码尺寸")
    private int size = 10;

    @TableField(exist = false)
    @ApiModelProperty(value = "页码")
    private int current = 1;


}
