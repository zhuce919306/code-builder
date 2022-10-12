<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${packageName}.mapper.${model.firstUpperCaseName}Mapper" >
<#--    <resultMap id="BaseResultMap" type="${packageName}.entity.${model.firstUpperCaseName}" >-->
<#--        <#list model.fieldList as field>-->
<#--            <#if field.pk>-->
<#--        <id column="${field.originalName}" property="${field.firstLowerCaseName}" jdbcType="${field.columnType}" />-->
<#--                <#else >-->
<#--        <result column="${field.originalName}" property="${field.firstLowerCaseName}" jdbcType="${field.columnType}" />-->
<#--            </#if>-->
<#--        </#list>-->
<#--    </resultMap>-->
</mapper>