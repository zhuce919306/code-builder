package com.pl.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.pl.core.exception.BusinessException;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;

/**
 * @ClasssName AssertUtil
 * @Description 断言工具类
 * @Author Liuyh
 * @Date 2021/6/18
 * @Version V0.0.1
 */
@UtilityClass
public class AssertUtil {
    /**
     * 生成消息
     *
     * @param tpl
     * @param args
     * @return
     */
    private String buildMessage(String tpl, Object... args) {
        String message = StrUtil.format(tpl, args);
        return message;
    }

    /**
     * 为false放行，为true抛出异常
     *
     * @param val
     * @param tpl
     * @param args
     */
    public void isFalse(boolean val, String tpl, Object... args) {
        if (val) {
            throw new BusinessException(buildMessage(tpl, args));
        }
    }

    /**
     * 为true放行，为false抛出异常
     *
     * @param val
     * @param tpl
     * @param args
     */
    public void isTrue(boolean val, String tpl, Object... args) {
        isFalse(!val, tpl, args);
    }

    public void isTrue(Throwable throwable, boolean val, String tpl, Object... args) {
        if (!val) {
            throw new BusinessException(buildMessage(tpl, args), throwable);
        }
    }

    public void isFalse(Throwable throwable, boolean val, String tpl, Object... args) {
        if (val) {
            throw new BusinessException(buildMessage(tpl, args), throwable);
        }
    }

    /**
     * 为空放行，不为空抛出异常
     *
     * @param o
     * @param tpl
     * @param args
     */
    public void isNull(Object o, String tpl, Object... args) {
        isTrue(o == null, tpl, args);
    }

    /**
     * 不为空放行，为空抛出异常
     *
     * @param o
     * @param tpl
     * @param args
     */
    public void notNull(Object o, String tpl, Object... args) {
        isTrue(o != null, tpl, args);
    }

    /**
     * 不为空
     *
     * @param message
     * @param objs
     */
    public void notNull(String message, Object... objs) {
        isTrue(ArrayUtil.isNotEmpty(objs), message);
    }

    /**
     * 对象为空放行，不为空抛出异常
     *
     * @param o
     * @param tpl
     * @param args
     */
    public void isEmpty(Object o, String tpl, Object... args) {
        isTrue(ObjectUtil.isEmpty(o), tpl, args);
    }

    /**
     * map为空放行，不为空抛出异常
     *
     * @param map
     * @param tpl
     * @param args
     */
    public void isEmpty(Map map, String tpl, Object... args) {
        isTrue(MapUtil.isEmpty(map), tpl, args);
    }

    /**
     * collection为空放行，不为空抛出异常
     *
     * @param collection
     * @param tpl
     * @param args
     */
    public void isEmpty(Collection<?> collection, String tpl, Object... args) {
        isTrue(CollectionUtil.isEmpty(collection), tpl, args);
    }

    /**
     * 数组为空放行，不为空抛出异常
     *
     * @param objs
     * @param tpl
     * @param args
     */
    public void isEmpty(Object[] objs, String tpl, Object... args) {
        isTrue(ArrayUtil.isEmpty(objs), tpl, args);
    }


    /**
     * 对象不为空放行，为空抛出异常
     *
     * @param o
     * @param tpl
     * @param args
     */
    public void notEmpty(Object o, String tpl, Object... args) {
        isTrue(ObjectUtil.isNotEmpty(o), tpl, args);
    }

    /**
     * map不为空放行，为空抛出异常
     *
     * @param map
     * @param tpl
     * @param args
     */
    public void notEmpty(Map map, String tpl, Object... args) {
        isTrue(MapUtil.isNotEmpty(map), tpl, args);
    }

    /**
     * collection不为空放行，为空抛出异常
     *
     * @param collection
     * @param tpl
     * @param args
     */
    public void notEmpty(Collection<?> collection, String tpl, Object... args) {
        isTrue(CollectionUtil.isNotEmpty(collection), tpl, args);
    }

    /**
     * 数组不为空放行，为空抛出异常
     *
     * @param objs
     * @param tpl
     * @param args
     */
    public void notEmpty(Object[] objs, String tpl, Object... args) {
        isTrue(ArrayUtil.isNotEmpty(objs), tpl, args);
    }

    /**
     * 是否为0
     *
     * @param val
     * @param tpl
     * @param args
     */
    public void isZero(Object val, String tpl, Object... args) {
        if (val == null) {
            throw new BusinessException("数值为空");
        }
        try {
            Long value = Long.parseLong(val.toString());
            isTrue(value == 0L, tpl, args);
        } catch (Exception e) {
            throw new BusinessException("数值格式错误");
        }
    }

    /**
     * 是否不为0
     *
     * @param val
     * @param tpl
     * @param args
     */
    public void notZero(Object val, String tpl, Object... args) {
        if (val == null) {
            throw new BusinessException("数值为空");
        }
        try {
            Long value = Long.parseLong(val.toString());
            isTrue(value != 0L, tpl, args);
        } catch (Exception e) {
            throw new BusinessException("数值格式错误");
        }
    }

    /**
     * 是否在之间
     *
     * @param val
     * @param min
     * @param max
     * @param eqMinAndMax
     * @param tpl
     * @param args
     */
    public void isBetween(Object val, Object min, Object max, boolean eqMinAndMax, String tpl, Object... args) {
        notNull("数值不能为空", val, min, max);
        Long valueNum = (Long) val;
        Long minNum = (Long) min;
        Long maxNum = (Long) max;
        if (eqMinAndMax) {
            isTrue(valueNum >= minNum && valueNum <= maxNum, tpl, args);
        } else {
            isTrue(valueNum > minNum && valueNum < maxNum, tpl, args);
        }
    }

    /**
     * 是否在之间(大于最小数，且小于最大数)
     *
     * @param val
     * @param min
     * @param max
     * @param tpl
     * @param args
     */
    public void isBetween(Object val, Object min, Object max, String tpl, Object... args) {
        isBetween(val, min, max, false, tpl, args);
    }

    /**
     * 是否在之间(≥最小数，且≤最大数)
     *
     * @param val
     * @param min
     * @param max
     * @param tpl
     * @param args
     */
    public void isBetweenEqMinAndMax(Object val, Object min, Object max, String tpl, Object... args) {
        isBetween(val, min, max, true, tpl, args);
    }


}