package com.pl.data.mybatis;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @ClasssName MyBatisMetaObjectHandler
 * @Description mybatis自动填充
 * @Author liuds
 * @Date 2021/7/6
 * @Version V0.0.1
 */
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        boolean isInsert = true;
        String userName = getUsername();
        this.setFill(isInsert, metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.setFill(isInsert, metaObject, "createUserId", String.class, userName);
        this.setFill(isInsert, metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.setFill(isInsert, metaObject, "updateUserId", String.class, userName);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        boolean isInsert = false;
        String userName = getUsername();
        this.setFill(isInsert, metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.setFill(isInsert, metaObject, "updateUserId", String.class, userName);
    }


    /**
     * 判断字段是否存在
     *
     * @param metaObject
     * @param fieldName
     * @return
     */
    private boolean fieldIsExist(MetaObject metaObject, String fieldName) {
        String name = Arrays.stream(metaObject.getGetterNames()).filter(item -> item.equals(fieldName)).findFirst().orElse(null);
        return StrUtil.isNotBlank(name);
    }


    private <T, E extends T> void setFill(boolean isInsert, MetaObject metaObject, String fieldName, Class<T> fieldType, E fieldVal) {
        if (fieldIsExist(metaObject, fieldName)) {
            if (isInsert) {
                this.strictInsertFill(metaObject, fieldName, fieldType, fieldVal);
            } else {
                this.strictUpdateFill(metaObject, fieldName, fieldType, fieldVal);
            }
        }
    }


    /**
     * 获取用户名称
     *
     * @return username
     */
    private String getUsername() {
        /*try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return null;
            }
            return authentication.getName();
        } catch (Exception e) {
            return null;
        }*/
        return null;
    }
}
