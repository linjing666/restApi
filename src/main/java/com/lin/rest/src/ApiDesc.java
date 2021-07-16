package com.lin.rest.src;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author linjing
 * @create 2021/7/15 11:23
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface ApiDesc {
    String desc() default "";
}
