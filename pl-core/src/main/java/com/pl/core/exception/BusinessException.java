package com.pl.core.exception;

import com.sun.xml.internal.ws.util.StringUtils;

/**
 * @ClasssName BusinessException
 * @Description 业务异常
 * @Author liuds
 * @Date 2021/7/12
 * @Version V0.0.1
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_FAULT_CODE = "-1";

    private String code;
    private String message;

    public BusinessException(String message) {
        this(DEFAULT_FAULT_CODE, message);

    }

    public BusinessException(String message, Throwable throwable) {
        this(DEFAULT_FAULT_CODE, message, throwable);

    }

    public BusinessException(String code, String message) {
        this(code, message, null);
    }

    public BusinessException(String code, String message, Throwable throwable) {
        super("[" + code + "] - " + message, throwable);
        this.message = message;
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessageWithoutCode() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BusinessException(Class clazz, String field, String val) {
        super(BusinessException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity) + " with " + field + " " + val + " existed";
    }
}
