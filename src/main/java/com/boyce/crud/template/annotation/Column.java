package com.boyce.crud.template.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description
 * @Author LiuY
 * @Date 2020/4/13 19:15
 * @Version V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
}
