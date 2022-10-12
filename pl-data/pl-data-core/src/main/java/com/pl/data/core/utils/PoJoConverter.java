package com.pl.data.core.utils;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClasssName PoJoConverter
 * @Description 对象转换器
 * @Author liuds
 * @Date 2021/7/30
 * @Version V0.0.1
 */
@UtilityClass
public class PoJoConverter {

    public static final String PO_2_VO = "po2vo";

    /**
     * 转换
     * @param type
     * @param collection
     * @param <T>
     * @return
     */
    public <T> List<T> convert(String type, Collection<?> collection) {
        List<T> resultList = new ArrayList<>();
        collection.forEach(item -> {
            T t = ReflectUtil.invoke(item, type);
            resultList.add(t);
        });
        return resultList;
    }

    /**
     * 集合po转vo
     * @param collection
     * @param <T>
     * @return
     */
    public <T> List<T> po2vo(Collection<?> collection) {
        return convert(PO_2_VO, collection);
    }

    /**
     * 分页po换vo
     * @param page
     * @param <T>
     * @return
     */
    public <T> Page<T> po2vo(Page<?> page) {
        List<?> collection = page.getRecords();
        Page<T> resultPage = new Page<>();
        resultPage.setTotal(page.getTotal());
        resultPage.setSize(page.getSize());
        resultPage.setCurrent(page.getCurrent());
        resultPage.setPages(page.getPages());
        resultPage.setRecords(convert(PO_2_VO, collection));
        return resultPage;
    }

    /**
     * 分页po换vo
     * @param page
     * @param <T>
     * @return
     */
    public <T> IPage<T> po2vo(IPage<?> page) {
        List<?> collection = page.getRecords();
        IPage<T> resultPage = new Page<>();
        resultPage.setTotal(page.getTotal());
        resultPage.setSize(page.getSize());
        resultPage.setCurrent(page.getCurrent());
        resultPage.setPages(page.getPages());
        resultPage.setRecords(convert(PO_2_VO, collection));
        return resultPage;
    }
}
