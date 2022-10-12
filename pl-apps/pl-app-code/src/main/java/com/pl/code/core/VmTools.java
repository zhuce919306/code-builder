package com.pl.code.core;

import cn.hutool.core.collection.CollectionUtil;
import com.pl.code.core.template.KeyValueTemplateData;
import com.pl.code.core.template.PackageDataModel;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.List;

/**
 * @ClasssName VmTools
 * @Description
 * @Author Liuyh
 * @Date 2021/5/25
 * @Version V0.0.1
 */
public class VmTools {
    /**
     * 生成泛型
     *
     * @param list
     * @return
     */
    public String buildGeneric(List<PackageDataModel> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            String generic = "";
            for (PackageDataModel item : list) {
                generic += ("," + item.getClassName());
            }
            return "<" + generic.substring(1) + ">";
        }
        return "";
    }

    /**
     * 生成注解属性
     *
     * @param properties
     * @return
     */
    public String buildAnnotayionProperties(List<KeyValueTemplateData> properties) {
        if (CollectionUtil.isEmpty(properties)) {
            return "";
        }
        String text = "";
        for (KeyValueTemplateData item : properties) {
            if (StringUtils.isBlank(item.getValue())){
                text += MessageFormat.format(",\"{0}\"", item.getKey());
            }else {
                text += MessageFormat.format(",{0}={1}", item.getKey(), item.getValue());
            }

        }
        if (text.length() > 0) {

            text = "(" + text.substring(1) + ")";
        }
        return text;
    }


    /**
     * 生成实现类
     * @param list
     * @return
     */
    public String buildImplements(List<PackageDataModel> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            String generic = "";
            for (PackageDataModel item : list) {
                generic += ("," + item.getClassName());
            }
            return generic.substring(1);
        }
        return "";
    }
}
