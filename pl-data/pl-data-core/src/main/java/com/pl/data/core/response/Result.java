package com.pl.data.core.response;

import com.pl.data.core.constants.BasicConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClasssName Result
 * @Description Restful响应信息主体
 * @Author liuds
 * @Date 2021/7/8
 * @Version V0.0.1
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long code;

    private String message;

    private T data;


    public static <T> Result<T> ok() {
        return build(BasicConstants.RESULT_CODE_SUCCESS, BasicConstants.RESULT_MESSAGE_SUCCESS, null);
    }


    public static <T> Result<T> ok(T data) {
        return build(BasicConstants.RESULT_CODE_SUCCESS, BasicConstants.RESULT_MESSAGE_SUCCESS, data);
    }

    public static <T> Result<T> ok(String message) {
        return build(BasicConstants.RESULT_CODE_SUCCESS, message, null);
    }


    public static <T> Result<T> ok(T data, String message) {
        return build(BasicConstants.RESULT_CODE_SUCCESS, message, data);
    }

    public static <T> Result<T> fail() {
        return build(BasicConstants.RESULT_CODE_FAIL, BasicConstants.RESULT_MESSAGE_FAIL, null);
    }


    public static <T> Result<T> fail(T data) {
        return build(BasicConstants.RESULT_CODE_FAIL, BasicConstants.RESULT_MESSAGE_FAIL, data);
    }

    public static <T> Result<T> fail(String message) {
        return build(BasicConstants.RESULT_CODE_FAIL, message, null);
    }


    public static <T> Result<T> fail(T data, String message) {
        return build(BasicConstants.RESULT_CODE_FAIL, message, data);
    }


    private static <T> Result<T> build(long code, String message, T data) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }
}
