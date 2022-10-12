package com.pl.core.utils;

import cn.hutool.json.JSONUtil;
import com.sun.istack.internal.Nullable;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @ClasssName WebUtil
 * @Description web工具类
 * @Author Liuyh
 * @Date 2021/7/12
 * @Version V0.0.1
 */
@UtilityClass
@Slf4j
public class WebUtil extends WebUtils {

    @Nullable
    public static HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(x -> (ServletRequestAttributes) x)
                .map(ServletRequestAttributes::getRequest)
                .orElse(null);
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return {HttpServletResponse}
     */
    @Nullable
    public static HttpServletResponse getResponse() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(x -> (ServletRequestAttributes) x)
                .map(ServletRequestAttributes::getResponse)
                .orElse(null);
    }

    public static void setCookie(HttpServletResponse response, String name, @Nullable String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @Nullable
    public static String getCookieVal(String name) {
        HttpServletRequest request = WebUtil.getRequest();
        if (request == null) {
            return null;
        }
        return getCookieVal(request, name);
    }

    /**
     * 读取cookie
     *
     * @param request HttpServletRequest
     * @param name    cookie name
     * @return cookie value
     */
    @Nullable
    public static String getCookieVal(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    @UtilityClass
    public static class Writer {

        /**
         * 写出json
         * @param response
         * @param obj
         */
        public void json(HttpServletResponse response, @Nullable Object obj) {
            String jsonText = JSONUtil.toJsonStr(obj);
            json(response, jsonText);
        }

        /**
         * 写出json
         * @param response
         * @param jsonText
         */
        public void json(HttpServletResponse response, @Nullable String jsonText) {
            if (jsonText != null) {
                text(response, jsonText, MediaType.APPLICATION_JSON_VALUE);
            }
        }

        /**
         * 写出文本
         * @param response
         * @param text
         * @param contentType
         */
        public void text(HttpServletResponse response, String text, String contentType) {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(contentType);
            try (PrintWriter out = response.getWriter()) {
                out.append(text);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
