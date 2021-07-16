package com.lin.rest.src;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * @Author linjing
 * @create 2021/7/15 11:23
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface ParamDesc {
    boolean require() default false;
    String desc() default "";
}
