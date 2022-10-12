package com.pl.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @ClasssName ValidRestController
 * @Description 参数验证控制层注解
 * @Author liuds
 * @Date 2020/12/16
 * @Version V0.0.1
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
public @interface PlRestController {
    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = Controller.class, attribute = "value")
    String controllerValue() default "";
}
