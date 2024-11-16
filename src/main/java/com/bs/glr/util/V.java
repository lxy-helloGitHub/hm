package com.bs.glr.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gonglqb
 * @name
 * @description
 * @create
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface V {
    boolean notNull() default false;
    //最大长度
    int minLen() default Integer.MIN_VALUE;
    //最小长度
    int maxLen() default Integer.MAX_VALUE;
    //固定长度
    int len() default -1;
}
