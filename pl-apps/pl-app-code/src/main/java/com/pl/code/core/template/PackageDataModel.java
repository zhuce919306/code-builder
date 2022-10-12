package com.pl.code.core.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @ClasssName PackageDataModel
 * @Description
 * @Author liuds
 * @Date 2021/5/29
 * @Version V0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageDataModel {
    private String packageName;
    private String className;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
