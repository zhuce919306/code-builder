package com.pl.data.core.constants;

/**
 * @ClasssName BasicConstants
 * @Description 基础公共常量
 * @Author liuds
 * @Date 2021/7/8
 * @Version V0.0.1
 */
public interface BasicConstants {
    /**
     * 接口状态码(成功)
     */
    Long RESULT_CODE_SUCCESS = 1L;

    /**
     * 接口状态码(失败)
     */
    Long RESULT_CODE_FAIL = 0L;

    /**
     * 接口消息(成功)
     */
    String RESULT_MESSAGE_SUCCESS = "成功";

    /**
     * 接口消息(失败)
     */
    String RESULT_MESSAGE_FAIL = "失败";
}
