<#assign superClass=""/>
<#assign superGeneric=""/>
<#assign implements=""/>
<#if model.extendsSupperClass>
    <#assign superClass= "extends " + model.superClassName/>
    <#assign superGeneric= vmTools.buildGeneric(model.superGenericList)/>
    <#assign implements= " implements " + vmTools.buildImplements(model.implementsList)/>
</#if>
package ${packageName}.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
<#if model.importPackages??>
    <#list model.importPackages as pkg>
import ${pkg};
    </#list>

</#if>
/**
 * ${model.comment}ServiceImpl
 * @author ${author}
 * @date ${date}
 * @version V0.0.1
 */
@Service
public class ${model.firstUpperCaseName}ServiceImpl ${implements} {
<#if model.wired>
    <#if model.wiredType == 'resource'>

    @Resource
    private ${model.firstUpperCaseName}Mapper ${model.firstLowerCaseName}Mapper;
    </#if>
    <#if model.wiredType == 'autoWired'>

    @Autowired
    private ${model.firstUpperCaseName}Mapper ${model.firstLowerCaseName}Mapper;
    </#if>
    <#if model.wiredType == 'constructor'>

    private final ${model.firstUpperCaseName}Mapper ${model.firstLowerCaseName}Mapper;

    public ${model.firstUpperCaseName}ServiceImpl(${model.firstUpperCaseName}Mapper ${model.firstLowerCaseName}Mapper) {
        this.${model.firstLowerCaseName}Mapper = ${model.firstLowerCaseName}Mapper;
    }
    </#if>
</#if>


    @Override
    public List<${model.firstUpperCaseName}> list(${model.firstUpperCaseName}Dto listQuery){

       List<${model.firstUpperCaseName}> list = ${model.firstLowerCaseName}Mapper.selectList(newQueryWrapper(listQuery));
       return list;
    }

    @Override
    public Page<${model.firstUpperCaseName}> page(${model.firstUpperCaseName}Dto listQuery){
       Page<${model.firstUpperCaseName}> page = new Page<${model.firstUpperCaseName}>();
       page.setSize(listQuery.getSize());
       page.setCurrent(listQuery.getCurrent());
       page = ${model.firstLowerCaseName}Mapper.selectPage(page,newQueryWrapper(listQuery));
       return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(${model.firstUpperCaseName} ${model.firstLowerCaseName}) {
       ${model.firstLowerCaseName}Mapper.insert(${model.firstLowerCaseName});
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(${model.firstUpperCaseName} ${model.firstLowerCaseName}) {
        //???????????????????????????
        // ${model.firstLowerCaseName}Mapper.update(${model.firstLowerCaseName},new UpdateWrapper<${model.firstUpperCaseName}>().eq("request_method", ${model.firstLowerCaseName}.getRequestMethod()));
        //??????????????????????????????
        // ${model.firstLowerCaseName}Mapper.update(${model.firstLowerCaseName},new QueryWrapper<${model.firstUpperCaseName}>().lambda().eq(${model.firstUpperCaseName}::getRequestMethod, ${model.firstLowerCaseName}.getRequestMethod()));
        //????????????id??????
         ${model.firstLowerCaseName}Mapper.updateById(${model.firstLowerCaseName});

        }

    @Override
    public ${model.firstUpperCaseName} findById(Long id) {
        ${model.firstUpperCaseName} info = ${model.firstLowerCaseName}Mapper.selectById(id);
        return info;
    }


    /**
     * ???????????????
     */
    private QueryWrapper<${model.firstUpperCaseName}> newQueryWrapper(${model.firstUpperCaseName}Dto listQuery) {
      //listQuery.setStatus(BackConstant.STATUS_0);

        QueryWrapper<${model.firstUpperCaseName}> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNull(listQuery)) {
        return queryWrapper;
        }

        Map map =  ObjToMapUtils.objToMap(listQuery);//????????????????????????????????????map

        //?????????????????????-????????????
        queryWrapper.like(ObjectUtil.isNotEmpty(listQuery.getName()), ColumnUtil.getFieldNameEntity(${model.firstUpperCaseName}::getName), listQuery.getName());
        map.remove(ColumnUtil.getFieldNameEntity(${model.firstUpperCaseName}::getName));


        //??????--????????????
        queryWrapper.orderByDesc(ColumnUtil.getFieldNameEntity(${model.firstUpperCaseName}::getUpdateDate));
        //????????????
        map.remove(ColumnUtil.getFieldNameEntity(${model.firstUpperCaseName}Dto::getCurrent));
        map.remove(ColumnUtil.getFieldNameEntity(${model.firstUpperCaseName}Dto::getSize));
        //map.remove("serial_version_u_i_d");
        queryWrapper.allEq(map, false);//false????????????map????????????

        return queryWrapper;
        }



}
