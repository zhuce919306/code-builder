package com.pl.common.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClasssName CollectionUtil
 * @Description 集合工具类
 * @Author liuds
 * @Date 2021/7/12
 * @Version V0.0.1
 */
@UtilityClass
public class CollectionUtil extends cn.hutool.core.collection.CollectionUtil {

    /**
     * 将key value 数组转为 map
     *
     * @param keysValues key value 数组
     * @param <K>        key
     * @param <V>        value
     * @return map 集合
     */
    public <K, V> Map<K, V> colToMap(Object... keysValues) {
        int kvLength = keysValues.length;
        if (kvLength % 2 != 0) {
            throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
        }
        Map<K, V> keyValueMap = new HashMap<>(kvLength);
        for (int i = kvLength - 2; i >= 0; i -= 2) {
            Object key = keysValues[i];
            Object value = keysValues[i + 1];
            keyValueMap.put((K) key, (V) value);
        }
        return keyValueMap;
    }

}
