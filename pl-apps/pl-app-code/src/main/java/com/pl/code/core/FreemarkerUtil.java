package com.pl.code.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.pl.code.common.CodeStyleEnum;
import com.pl.code.common.GenerateIdTypeEnum;
import com.pl.code.common.TemplateMapperEnum;
import com.pl.code.config.properties.*;
import com.pl.code.core.template.ClassTemplateData;
import com.pl.code.core.template.ControllerTemplateData;
import com.pl.code.core.template.FieldTemplateData;
import com.pl.code.core.template.PackageDataModel;
import com.pl.code.entity.po.TableColumn;
import com.pl.code.entity.po.TableInfo;
import com.pl.code.mapper.CodeGenerateMapper;
import com.pl.core.exception.BusinessException;
import com.pl.core.utils.AssertUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClasssName FtlGenerateUtil
 * @Description
 * @Author liuds
 * @Date 2021/5/28
 * @Version V0.0.1
 */
@Component
public class FreemarkerUtil {
    @Resource
    private CodeGenerateMapper codeGenerateMapper;
    @Resource
    private ConfigAdapter configAdapter;


    private Map<String, Object> createDataModel(String packageName, String author, String moduleName, ClassTemplateData templateData, GenerateConfig config) {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("model", templateData);
        dataModel.put("packageName", StringUtils.isNotBlank(moduleName) ? packageName + "." + moduleName : packageName);
        dataModel.put("author", author);
        dataModel.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd mm:HH:ss")));
        dataModel.put("vmTools", new VmTools());
        dataModel.put("swagger", config.isSwagger());
        dataModel.put("path", templateData.getFirstLowerCaseName().toLowerCase());
        dataModel.put("style", config.getStyle());
        return dataModel;
    }

    @SneakyThrows
    public String generateText(String templateName, GenerateConfig config) {
        TableInfo tableInfo = codeGenerateMapper.selectTableInfo(config.getTableName(), config.getDsName());
        List<TableColumn> columnList = codeGenerateMapper.selectTableColumn(config.getTableName(), config.getDsName());
        String className = GeneratorUtil.tableToClsName(config.getTableName(), config.getTablePrefix());


        // 通过FreeMarker的Confuguration读取相应的模板文件
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        // 设置模板路径
        configuration.setClassForTemplateLoading(this.getClass(), "/template/ftl");
        // 设置默认字体
        configuration.setDefaultEncoding("utf-8");
        TemplateMapperEnum templateMapper = TemplateMapperEnum.nameOf(templateName);
        AssertUtil.notNull(templateMapper,"未知模板");
        ClassTemplateData entityTemplateData = null;
        switch (templateMapper) {
            case ENTITY:
                entityTemplateData = buildEntityTemplateData(className, tableInfo, columnList, config);
                break;
            case MAPPER:
                entityTemplateData = buildMapperTemplateData(className, tableInfo, config);
                break;
            case MAPPER_XML:
                entityTemplateData = buildMapperXmlTemplateData(className, tableInfo, columnList, config);
                break;
            case SERVICE:
                entityTemplateData = buildServiceTemplateData(className, tableInfo, config);
                break;
            case SERVICE_IMPL:
                entityTemplateData = buildServiceImplTemplateData(className, tableInfo, config);
                break;
            case CONTROLLER:
                entityTemplateData = buildControllerTemplateData(className, tableInfo, columnList, config);
                break;
        }
        Map<String, Object> dataModel = createDataModel(config.getPackageName(), config.getAuthor(), config.getModuleName(), entityTemplateData, config);
        // 获取模板
        Template template = configuration.getTemplate(templateMapper.getTemplate());
        StringWriter sw = new StringWriter();
        template.process(dataModel, sw);
        String result = sw.toString();
        IoUtil.close(sw);
        return result;
    }

    @SneakyThrows
    public void generateByte(String templateName, GenerateConfig config, ZipOutputStream zip, String className, TableInfo tableInfo, List<TableColumn> columnList) {


        // 通过FreeMarker的Confuguration读取相应的模板文件
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        // 设置模板路径
        configuration.setClassForTemplateLoading(this.getClass(), "/template/ftl");
        // 设置默认字体
        configuration.setDefaultEncoding("utf-8");
        TemplateMapperEnum templateMapper = TemplateMapperEnum.nameOf(templateName);
        if (templateMapper == null) {
            throw new BusinessException("未知模板");
        }
        ClassTemplateData entityTemplateData = null;
        switch (templateMapper) {
            case ENTITY:
                entityTemplateData = buildEntityTemplateData(className, tableInfo, columnList, config);
                break;
            case MAPPER:
                entityTemplateData = buildMapperTemplateData(className, tableInfo, config);
                break;
            case MAPPER_XML:
                entityTemplateData = buildMapperXmlTemplateData(className, tableInfo, columnList, config);
                break;
            case SERVICE:
                entityTemplateData = buildServiceTemplateData(className, tableInfo, config);
                break;
            case SERVICE_IMPL:
                entityTemplateData = buildServiceImplTemplateData(className, tableInfo, config);
                break;
            case CONTROLLER:
                entityTemplateData = buildControllerTemplateData(className, tableInfo, columnList, config);
                break;
        }
        Map<String, Object> dataModel = createDataModel(config.getPackageName(), config.getAuthor(), config.getModuleName(), entityTemplateData, config);
        // 获取模板
        Template template = configuration.getTemplate(templateMapper.getTemplate());
        StringWriter sw = new StringWriter();
        template.process(dataModel, sw);
        String fileName = getFileFullPath(templateName, config);
        if (zip != null) {
            zip.putNextEntry(new ZipEntry(Objects.requireNonNull(fileName)));
            IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
            IoUtil.close(sw);
            zip.closeEntry();
        }
    }

    public String getFileFullPath(String templateName, GenerateConfig config) {
        String packageName = config.getPackageName();
        String moduleName = config.getModuleName();
        String packagePath = "pl-web-api" + File.separator + "src" + File.separator + "main"
                + File.separator + "java" + File.separator;
        String resourcePackagePath = "pl-web-api" + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator;
        String className = GeneratorUtil.tableToClsName(config.getTableName(), config.getTablePrefix());
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (StringUtils.isNotBlank(moduleName)) {
            packagePath += moduleName + File.separator;
        }
        TemplateMapperEnum templateMapper = TemplateMapperEnum.nameOf(templateName);
        if (templateMapper == null) {
            throw new BusinessException("未知模板");
        }
        switch (templateMapper) {
            case ENTITY:
                return packagePath + "entity" + File.separator + className + ".java";
            case MAPPER:
                return packagePath + "mapper" + File.separator + className + "Mapper.java";
            case MAPPER_XML:
                return resourcePackagePath + "mapper" + File.separator + className + "Mapper.xml";
            case SERVICE:
                return packagePath + "service" + File.separator + className + "Service.java";
            case SERVICE_IMPL:
                return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
            case CONTROLLER:
                return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        return null;
    }


    public byte[] download(List<String> templates, GenerateConfig config) {
        TableInfo tableInfo = codeGenerateMapper.selectTableInfo(config.getTableName(), config.getDsName());
        List<TableColumn> columnList = codeGenerateMapper.selectTableColumn(config.getTableName(), config.getDsName());
        String className = GeneratorUtil.tableToClsName(config.getTableName(), config.getTablePrefix());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        if (templates.contains("Entity")) {
            generateByte("Entity", config, zip, className, tableInfo, columnList);
        }

        if (templates.contains("Mapper")) {
            generateByte("Mapper", config, zip, className, tableInfo, columnList);
        }

        if (templates.contains("MapperXML")) {
            generateByte("MapperXML", config, zip, className, tableInfo, columnList);
        }

        if (templates.contains("Service")) {
            generateByte("Service", config, zip, className, tableInfo, columnList);
        }

        if (templates.contains("ServiceImpl")) {
            generateByte("ServiceImpl", config, zip, className, tableInfo, columnList);
        }

        if (templates.contains("Controller")) {
            generateByte("Controller", config, zip, className, tableInfo, columnList);
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    public GenerateResult generate(List<String> templates, GenerateConfig config) {

        GenerateResult result = new GenerateResult();
        if (templates.contains("Entity")) {
            String entity = generateText("Entity", config);
            result.setEntity(entity);
        }

        if (templates.contains("Mapper")) {
            String mapper = generateText("Mapper", config);
            result.setMapper(mapper);
        }

        if (templates.contains("MapperXML")) {
            String mapperXml = generateText("MapperXML", config);
            result.setMapperXml(mapperXml);
        }

        if (templates.contains("Service")) {
            String service = generateText("Service", config);
            result.setService(service);
        }


        if (templates.contains("ServiceImpl")) {
            String serviceImpl = generateText("ServiceImpl", config);
            result.setServiceImpl(serviceImpl);
        }

        if (templates.contains("Controller")) {
            String controller = generateText("Controller", config);
            result.setController(controller);
        }
        return result;
    }


    /**
     * 生成Entity数据模型
     *
     * @param className
     * @param tableInfo
     * @param columnList
     * @param config
     * @return
     */
    private ClassTemplateData buildEntityTemplateData(String className, TableInfo tableInfo, List<TableColumn> columnList, GenerateConfig config) {
        EntityConfigProperties entityConfig = configAdapter.getEntityConfig(config);
        LombokConfigProperties lombokConfig = configAdapter.getLombokConfig(config);
        MapperConfigProperties mapperConfig = configAdapter.getMapperConfig(config);
        ColumnConfigProperties columnConfig = configAdapter.getColumnConfig(config);
        List<TypeMapperConfigProperties> typeMapperConfig = configAdapter.getTypeMapperConfig(config);
        String comment = StringUtils.isBlank(config.getCodeAnnotation()) ? tableInfo.getTableComment() : config.getCodeAnnotation();
        String firstUpperCaseAndHumpName = GeneratorUtil.firstUpperCaseAndHump(className);
        ClassTemplateData entityTemplateData = new ClassTemplateData();
        entityTemplateData.setOriginalName(tableInfo.getTableName());
        entityTemplateData.setComment(comment);
        entityTemplateData.setFirstUpperCaseName(firstUpperCaseAndHumpName);
        entityTemplateData.setFirstLowerCaseName(GeneratorUtil.firstLowerCaseAndHump(className));
        entityTemplateData.setHumpName(StringUtils.uncapitalize(className));
        List<String> importPackageList = new ArrayList<>();
        // 判断是否需要继承父类
        boolean extendsSuperClass = false;

        // 父类包名
        String superClassPackageName = null;

        // 泛型
        List<PackageDataModel> genericList = new ArrayList<>();
        CodeStyleEnum codeStyleEnum = CodeStyleEnum.ofValue(config.getStyle());
        switch (codeStyleEnum) {
            case PL_WEB:
                extendsSuperClass = true;
                superClassPackageName = StringUtils.isNotBlank(entityConfig.getSuperClassPackage()) ? entityConfig.getSuperClassPackage() : "com.pl.data.core.entity.po.AbstractPlEntity";
                entityTemplateData.addGenericClass(GeneratorUtil.packageToClassName(superClassPackageName, firstUpperCaseAndHumpName));
                break;
            case MYBATIS_PLUS:
                extendsSuperClass = true;
                superClassPackageName = StringUtils.isNotBlank(entityConfig.getSuperClassPackage()) ? entityConfig.getSuperClassPackage() : "com.baomidou.mybatisplus.extension.activerecord.Model";
                entityTemplateData.addGenericClass(GeneratorUtil.packageToClassName(superClassPackageName, firstUpperCaseAndHumpName));
                break;
            default:
                extendsSuperClass = entityConfig.isExtendsSuper() && StringUtils.isNotBlank(entityConfig.getSuperClassPackage());
                if (extendsSuperClass) {
                    superClassPackageName = entityConfig.getSuperClassPackage();
                }
                break;
        }

        if (extendsSuperClass) {
            GeneratorUtil.putPackage(importPackageList, superClassPackageName);
        }
        entityTemplateData.setExtendsSupperClass(extendsSuperClass);
        entityTemplateData.setSuperClassPackageName(superClassPackageName);
        // 添加父类泛型
        if (CollectionUtil.isNotEmpty(entityConfig.getSuperClassGenericPackage())) {
            entityConfig.getSuperClassGenericPackage().forEach(item -> {
                PackageDataModel packageModel = GeneratorUtil.packageToClassName(item, null);
                entityTemplateData.addGenericClass(packageModel);
                GeneratorUtil.putPackage(importPackageList, packageModel);
            });
        }
        // 成员属性相关
        List<String> insertAutoFillColumns = columnConfig.getInsertFillColumns();
        List<String> updateAutoFillColumns = columnConfig.getUpdateFillColumns();
        List<String> insertOrUpdateAutoFillColumns = columnConfig.getInsertOrUpdateFillColumns();
        List<String> hiddenColumns = columnConfig.getHiddenColumns();
        List<String> commonColumns = columnConfig.getCommonColumns();
        List<String> logicDeleteColumns = columnConfig.getLogicDeleteColumns();
        // 字段
        boolean isMyBatisPlus = CodeStyleEnum.MYBATIS_PLUS.equals(codeStyleEnum) || CodeStyleEnum.PL_WEB.equals(codeStyleEnum);
        columnList.forEach(column -> {
            FieldTemplateData field = FieldTemplateData.builder().build();
            boolean isCommon = GeneratorUtil.columnExist(commonColumns, column.getColumnName());
            boolean isHidden = false;
            if (isCommon) {
                isHidden = true;
            } else {
                isHidden = GeneratorUtil.columnExist(hiddenColumns, column.getColumnName());
            }
            // 是否为逻辑删除字段
            boolean logicDelete = GeneratorUtil.columnExist(logicDeleteColumns, column.getColumnName());
            // 是否insert自动填充
            boolean insertFill = GeneratorUtil.columnExist(insertAutoFillColumns, column.getColumnName());
            // 是否update自动填充
            boolean updateFill = GeneratorUtil.columnExist(updateAutoFillColumns, column.getColumnName());
            // 是否insert或update自动填充
            boolean insertOrUpdateFill = GeneratorUtil.columnExist(insertOrUpdateAutoFillColumns, column.getColumnName());
            // 是否为主键
            boolean isPk = "pri".equalsIgnoreCase(column.getColumnKey());
            String fieldJavaName = GeneratorUtil.col2java(column.getColumnName());
            field.setOriginalName(column.getColumnName());
            field.setCommon(isCommon);
            field.setHumpName(fieldJavaName);
            field.setFirstUpperCaseName(fieldJavaName);
            field.setFirstLowerCaseName(GeneratorUtil.firstLowerCaseAndHump(fieldJavaName));
            TypeMapperConfigProperties fieldType = GeneratorUtil.colType2JavaType(typeMapperConfig, column.getDataType());
            field.setType(fieldType.getJavaType());
            field.setPackageName(fieldType.getPackageName());
            field.setPk(isPk);
            field.setComment(column.getComments());
            field.setHidden(isHidden);
            field.setLogicDelete(logicDelete);
            field.setInsertFill(insertFill);
            field.setUpdateFill(updateFill);
            field.setInsertOrUpdateFill(insertOrUpdateFill);

            if (!isHidden && fieldType.isImportPackage()){
                GeneratorUtil.putPackage(importPackageList, fieldType.getPackageName());
            }

            if (!isHidden && isMyBatisPlus) {
                if (isPk) {
                    GeneratorUtil.putPackage(importPackageList, "com.baomidou.mybatisplus.annotation.TableId");
                    GenerateIdTypeEnum idTypeEnum = GenerateIdTypeEnum.nameOf(mapperConfig.getIdType());
                    if (idTypeEnum != null) {
                        GeneratorUtil.putPackage(importPackageList, "com.baomidou.mybatisplus.annotation.IdType");
                        String propertiesText = "type=" + idTypeEnum.getValue();
                        field.addAnnotation("@TableId", propertiesText);
                    } else {
                        field.addAnnotation("@TableId", null);
                    }

                }
                if (logicDelete || insertFill || updateFill || insertOrUpdateFill) {
                    GeneratorUtil.putPackage(importPackageList, "com.baomidou.mybatisplus.annotation.TableLogic");
                    GeneratorUtil.putPackage(importPackageList, "com.baomidou.mybatisplus.annotation.FieldFill");
                }
                field.addAnnotation("@TableLogic", null, logicDelete);
                field.addAnnotation("@TableField", "fill=FieldFill.INSERT", insertFill);
                field.addAnnotation("@TableField", "fill=FieldFill.UPDATE", updateFill);
                field.addAnnotation("@TableField", "fill=FieldFill.INSERT_UPDATE", insertOrUpdateFill);
            }
            if (config.isSwagger()) {
                field.addAnnotation("@ApiModelProperty", StrUtil.format("value=\"{}\"&name=\"{}\"&dataType=\"{}\"", column.getComments(), field.getFirstLowerCaseName(), fieldType.getJavaType().toLowerCase()));
                GeneratorUtil.putPackage(importPackageList, "io.swagger.annotations.ApiModelProperty");
            }
            entityTemplateData.addField(field);
        });

        entityTemplateData.setImportPackages(importPackageList);
        // lombok
        if (lombokConfig.getEnable()) {
            if (lombokConfig.hasAnnotation()) {
                GeneratorUtil.putPackage(importPackageList, "lombok.*");
            }
            if (lombokConfig.getBuilder()) {
                entityTemplateData.addAnnotation("@Builder", null);
            }
            if (lombokConfig.getData()) {
                entityTemplateData.addAnnotation("@Data", null);
            }
            if (lombokConfig.getAllArgsConstructor()) {
                entityTemplateData.addAnnotation("@AllArgsConstructor", null);
            }
            if (lombokConfig.getNoAllArgsConstructor()) {
                entityTemplateData.addAnnotation("@NoArgsConstructor", null);
            }
            if (lombokConfig.getGetter()) {
                entityTemplateData.addAnnotation("@Getter", null);
            }
            if (lombokConfig.getSetter()) {
                entityTemplateData.addAnnotation("@Setter", null);
            }
            if (lombokConfig.getToStr()) {
                entityTemplateData.addAnnotation("@toString", null);
            }
            if (lombokConfig.getEqualsAndHashCode()) {
                entityTemplateData.addAnnotation("@EqualsAndHashCode", null);
            }
            if (lombokConfig.isEnableAccessors()) {
                entityTemplateData.addAnnotation("@Accessors", lombokConfig.getAccessorsPropertity());
            }
        }

        // swagger
        if (config.isSwagger()) {
            entityTemplateData.addAnnotation("@ApiModel", tableInfo.getTableComment());
            GeneratorUtil.putPackage(importPackageList, "io.swagger.annotations.ApiModel");
        }
        return entityTemplateData;
    }

    /**
     * 生成Mapper数据模型
     *
     * @param className
     * @param tableInfo
     * @param config
     * @return
     */
    private ClassTemplateData buildMapperTemplateData(String className, TableInfo tableInfo, GenerateConfig config) {
        MapperConfigProperties mapperConfig = configAdapter.getMapperConfig(config);
        String comment = StringUtils.isBlank(config.getCodeAnnotation()) ? tableInfo.getTableComment() : config.getCodeAnnotation();
        ClassTemplateData classTemplateData = new ClassTemplateData();
        classTemplateData.setOriginalName(tableInfo.getTableName());
        classTemplateData.setComment(comment);
        classTemplateData.setFirstUpperCaseName(GeneratorUtil.firstUpperCaseAndHump(className));
        classTemplateData.setFirstLowerCaseName(GeneratorUtil.firstLowerCaseAndHump(className));
        classTemplateData.setHumpName(StringUtils.uncapitalize(className));

        List<String> importPackageList = new ArrayList<>();

        if (mapperConfig.isMapperAnnotation()) {
            GeneratorUtil.putPackage(importPackageList, "org.apache.ibatis.annotations.Mapper");
            classTemplateData.addAnnotation("@Mapper", null);
        }
        // 判断是否需要继承父类
        boolean extendsSuperClass = false;
        String superClassPackageName = null;
        // 泛型
        List<PackageDataModel> genericList = new ArrayList<>();
        CodeStyleEnum codeStyleEnum = CodeStyleEnum.ofValue(config.getStyle());
        if (CodeStyleEnum.PL_WEB.equals(codeStyleEnum) || CodeStyleEnum.MYBATIS_PLUS.equals(codeStyleEnum)) {
            extendsSuperClass = true;
            superClassPackageName = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
            GeneratorUtil.putPackage(importPackageList, superClassPackageName);
            String entityPackageName = (StringUtils.isNotBlank(config.getModuleName()) ? config.getPackageName() + "." + config.getModuleName() : config.getPackageName()) + ".entity." + classTemplateData.getFirstUpperCaseName();
            genericList.add(GeneratorUtil.packageToClassName(entityPackageName, null));
            GeneratorUtil.putPackage(importPackageList, entityPackageName);

        }
        classTemplateData.setExtendsSupperClass(extendsSuperClass);
        classTemplateData.setSuperClassPackageName(superClassPackageName);
        classTemplateData.setImportPackages(importPackageList);
        genericList.forEach(item -> {
            PackageDataModel packageModel = GeneratorUtil.packageToClassName(item.getPackageName(), classTemplateData.getFirstUpperCaseName());
            classTemplateData.addGenericClass(packageModel);
        });
        return classTemplateData;
    }


    /**
     * 生成Mapper.xml数据模型
     *
     * @param className
     * @param tableInfo
     * @param columnList
     * @param config
     * @return
     */
    private ClassTemplateData buildMapperXmlTemplateData(String className, TableInfo tableInfo, List<TableColumn> columnList, GenerateConfig config) {
        String comment = StringUtils.isBlank(config.getCodeAnnotation()) ? tableInfo.getTableComment() : config.getCodeAnnotation();
        ClassTemplateData classTemplateData = new ClassTemplateData();
        classTemplateData.setOriginalName(tableInfo.getTableName());
        classTemplateData.setComment(comment);
        classTemplateData.setFirstUpperCaseName(GeneratorUtil.firstUpperCaseAndHump(className));
        classTemplateData.setFirstLowerCaseName(GeneratorUtil.firstLowerCaseAndHump(className));
        classTemplateData.setHumpName(StringUtils.uncapitalize(className));
        columnList.forEach(column -> {
            FieldTemplateData field = FieldTemplateData.builder().build();
            boolean isPk = "pri".equalsIgnoreCase(column.getColumnKey());
            String fieldJavaName = GeneratorUtil.col2java(column.getColumnName());
            field.setOriginalName(column.getColumnName());
            field.setColumnType(column.getDataType().toUpperCase());
            field.setFirstLowerCaseName(GeneratorUtil.firstLowerCaseAndHump(fieldJavaName));
            field.setPk(isPk);
            classTemplateData.addField(field);
        });
        return classTemplateData;
    }

    /**
     * 生成Service.java数据模型
     *
     * @param className
     * @param tableInfo
     * @param config
     * @return
     */
    private ClassTemplateData buildServiceTemplateData(String className, TableInfo tableInfo, GenerateConfig config) {
        String comment = StringUtils.isBlank(config.getCodeAnnotation()) ? tableInfo.getTableComment() : config.getCodeAnnotation();
        ClassTemplateData classTemplateData = new ClassTemplateData();
        classTemplateData.setOriginalName(tableInfo.getTableName());
        classTemplateData.setComment(comment);
        classTemplateData.setFirstUpperCaseName(GeneratorUtil.firstUpperCaseAndHump(className));
        classTemplateData.setFirstLowerCaseName(GeneratorUtil.firstLowerCaseAndHump(className));
        classTemplateData.setHumpName(StringUtils.uncapitalize(className));
        List<String> importPackageList = new ArrayList<>();
        // 判断是否需要继承父类
        boolean extendsSuperClass = false;
        String superClassPackageName = null;
        // 泛型
        List<PackageDataModel> genericList = new ArrayList<>();
        CodeStyleEnum codeStyleEnum = CodeStyleEnum.ofValue(config.getStyle());
        if (CodeStyleEnum.PL_WEB.equals(codeStyleEnum) || CodeStyleEnum.MYBATIS_PLUS.equals(codeStyleEnum)) {
            extendsSuperClass = true;
            superClassPackageName = "com.baomidou.mybatisplus.extension.service.IService";
            GeneratorUtil.putPackage(importPackageList, superClassPackageName);
            String entityPackageName = (StringUtils.isNotBlank(config.getModuleName()) ? config.getPackageName() + "." + config.getModuleName() : config.getPackageName()) + ".entity." + classTemplateData.getFirstUpperCaseName();
            genericList.add(GeneratorUtil.packageToClassName(entityPackageName, null));
            GeneratorUtil.putPackage(importPackageList, entityPackageName);
        }
        classTemplateData.setExtendsSupperClass(extendsSuperClass);
        classTemplateData.setSuperClassPackageName(superClassPackageName);
        classTemplateData.setImportPackages(importPackageList);
        genericList.forEach(item -> {
            PackageDataModel packageModel = GeneratorUtil.packageToClassName(item.getPackageName(), classTemplateData.getFirstUpperCaseName());
            classTemplateData.addGenericClass(packageModel);
        });
        return classTemplateData;
    }


    /**
     * 生成ServiceImpl.java数据模型
     *
     * @param className
     * @param tableInfo
     * @param config
     * @return
     */
    private ClassTemplateData buildServiceImplTemplateData(String className, TableInfo tableInfo, GenerateConfig config) {
        String comment = StringUtils.isBlank(config.getCodeAnnotation()) ? tableInfo.getTableComment() : config.getCodeAnnotation();
        ClassTemplateData classTemplateData = new ClassTemplateData();
        classTemplateData.setOriginalName(tableInfo.getTableName());
        classTemplateData.setComment(comment);
        classTemplateData.setFirstUpperCaseName(GeneratorUtil.firstUpperCaseAndHump(className));
        classTemplateData.setFirstLowerCaseName(GeneratorUtil.firstLowerCaseAndHump(className));
        classTemplateData.setHumpName(StringUtils.uncapitalize(className));
        List<String> importPackageList = new ArrayList<>();
        // 判断是否需要继承父类
        boolean extendsSuperClass = false;
        String superClassPackageName = null;
        // 泛型
        List<PackageDataModel> genericList = new ArrayList<>();
        CodeStyleEnum codeStyleEnum = CodeStyleEnum.ofValue(config.getStyle());
        if (CodeStyleEnum.PL_WEB.equals(codeStyleEnum) || CodeStyleEnum.MYBATIS_PLUS.equals(codeStyleEnum)) {
            extendsSuperClass = true;
            superClassPackageName = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
            GeneratorUtil.putPackage(importPackageList, superClassPackageName);
            String entityPackageName = (StringUtils.isNotBlank(config.getModuleName()) ? config.getPackageName() + "." + config.getModuleName() : config.getPackageName()) + ".entity." + classTemplateData.getFirstUpperCaseName();
            String mapperPackageName = (StringUtils.isNotBlank(config.getModuleName()) ? config.getPackageName() + "." + config.getModuleName() : config.getPackageName()) + ".mapper." + classTemplateData.getFirstUpperCaseName() + "Mapper";

            genericList.add(GeneratorUtil.packageToClassName(mapperPackageName, null));
            GeneratorUtil.putPackage(importPackageList, mapperPackageName);

            genericList.add(GeneratorUtil.packageToClassName(entityPackageName, null));
            GeneratorUtil.putPackage(importPackageList, entityPackageName);
        }
        classTemplateData.setExtendsSupperClass(extendsSuperClass);
        classTemplateData.setSuperClassPackageName(superClassPackageName);


        // 实现类
        List<PackageDataModel> implementsList = new ArrayList<>();
        String servicePackageName = (StringUtils.isNotBlank(config.getModuleName()) ? config.getPackageName() + "." + config.getModuleName() : config.getPackageName()) + ".service." + classTemplateData.getFirstUpperCaseName() + "Service";
        implementsList.add(GeneratorUtil.packageToClassName(servicePackageName, null));
        GeneratorUtil.putPackage(importPackageList, servicePackageName);


        classTemplateData.setWired(config.isWired());
        classTemplateData.setWiredType(config.getWiredType());
        if (config.isWired()) {
            if ("resource".equalsIgnoreCase(config.getWiredType())) {
                GeneratorUtil.putPackage(importPackageList, "javax.annotation.Resource");
            }
            if ("Autowired".equalsIgnoreCase(config.getWiredType())) {
                GeneratorUtil.putPackage(importPackageList, "org.springframework.beans.factory.annotation.Autowired");
            }
        }


        classTemplateData.setImportPackages(importPackageList);
        genericList.forEach(item -> {
            classTemplateData.addGenericClass(item);
        });
        implementsList.forEach(item -> {
            classTemplateData.addImplement(item);
        });
        return classTemplateData;
    }

    /**
     * 生成Controller.java数据模型
     *
     * @param className
     * @param tableInfo
     * @param columnList
     * @param config
     * @return
     */
    private ClassTemplateData buildControllerTemplateData(String className, TableInfo tableInfo, List<TableColumn> columnList, GenerateConfig config) {
        List<TypeMapperConfigProperties> typeMapperConfig = configAdapter.getTypeMapperConfig(config);
        String comment = StringUtils.isBlank(config.getCodeAnnotation()) ? tableInfo.getTableComment() : config.getCodeAnnotation();
        ControllerConfigProperties controllerConfig = config.getControllerConfig();
        ControllerTemplateData classTemplateData = new ControllerTemplateData();
        classTemplateData.setOriginalName(tableInfo.getTableName());
        classTemplateData.setComment(comment);
        classTemplateData.setFirstUpperCaseName(GeneratorUtil.firstUpperCaseAndHump(className));
        classTemplateData.setFirstLowerCaseName(GeneratorUtil.firstLowerCaseAndHump(className));
        classTemplateData.setHumpName(StringUtils.uncapitalize(className));
        classTemplateData.setBasicApi(controllerConfig.isBasicApi());

        classTemplateData.setWired(config.isWired());
        classTemplateData.setWiredType(config.getWiredType());

        TableColumn pkColumn = getPk(columnList);
        if (pkColumn != null) {
            TypeMapperConfigProperties fieldType = GeneratorUtil.colType2JavaType(typeMapperConfig, pkColumn.getDataType());
            classTemplateData.setPkJavaType(fieldType.getJavaType());

            String fieldJavaName = GeneratorUtil.col2java(pkColumn.getColumnName());
            classTemplateData.setPkName(GeneratorUtil.firstLowerCaseAndHump(fieldJavaName));
        } else {
            classTemplateData.setPkJavaType("Serializable");
            classTemplateData.setPkName("id");
        }

        List<String> importPackageList = new ArrayList<>();
        // 判断是否需要继承父类
        boolean extendsSuperClass = false;
        String superClassPackageName = null;
        // 泛型
        List<PackageDataModel> genericList = new ArrayList<>();
        CodeStyleEnum codeStyleEnum = CodeStyleEnum.ofValue(config.getStyle());
        if (CodeStyleEnum.PL_WEB.equals(codeStyleEnum)) {
            classTemplateData.addAnnotation("@PlRestController", classTemplateData.getFirstLowerCaseName().toLowerCase());
            GeneratorUtil.putPackage(importPackageList, "com.pl.core.annotation.PlRestController");
            config.setStyle(CodeStyleEnum.PL_WEB.getValue());
            if (controllerConfig.isBasicApi()) {
                classTemplateData.setResponseClassName("Result");
                classTemplateData.setResponsePackageName("com.pl.core.response.Result");
                classTemplateData.setSuccessMethod("success");
                classTemplateData.setErrorMethod("fail");
            }
        } else {
            classTemplateData.addAnnotation("@RestController", null);
            classTemplateData.addAnnotation("@RequestMapping", classTemplateData.getFirstLowerCaseName().toLowerCase());
            GeneratorUtil.putPackage(importPackageList, "org.springframework.web.bind.annotation.*");
            String resultPackageName = controllerConfig.getResponseEntity().getPackageName();
            if (StringUtils.isBlank(resultPackageName)) {
                throw new BusinessException("Response实体包名不能为空");
            }
            classTemplateData.setResponseClassName(GeneratorUtil.packageToClassName(resultPackageName, null).getClassName());
            classTemplateData.setResponsePackageName(resultPackageName);
            classTemplateData.setSuccessMethod(controllerConfig.getResponseEntity().getSuccessMethod());
            classTemplateData.setErrorMethod(controllerConfig.getResponseEntity().getErrorMethod());
        }

        if (CodeStyleEnum.PL_WEB.equals(codeStyleEnum) || CodeStyleEnum.MYBATIS_PLUS.equals(codeStyleEnum)) {
            GeneratorUtil.putPackage(importPackageList, "com.baomidou.mybatisplus.core.toolkit.Wrappers");
        }

        classTemplateData.setExtendsSupperClass(extendsSuperClass);
        classTemplateData.setSuperClassPackageName(superClassPackageName);


        if (config.isWired()) {
            if ("resource".equalsIgnoreCase(config.getWiredType())) {
                GeneratorUtil.putPackage(importPackageList, "javax.annotation.Resource");
            }
            if ("Autowired".equalsIgnoreCase(config.getWiredType())) {
                GeneratorUtil.putPackage(importPackageList, "org.springframework.beans.factory.annotation.Autowired");
            }
        }


        classTemplateData.setImportPackages(importPackageList);
        genericList.forEach(item -> {
            classTemplateData.addGenericClass(item);
        });
        return classTemplateData;
    }

    /**
     * 获取主键
     *
     * @param columnList
     * @return
     */
    private TableColumn getPk(List<TableColumn> columnList) {
        for (TableColumn column : columnList) {
            // 是否为主键
            boolean isPk = "pri".equalsIgnoreCase(column.getColumnKey());
            if (isPk) {
                return column;
            }
        }
        return null;
    }

}
