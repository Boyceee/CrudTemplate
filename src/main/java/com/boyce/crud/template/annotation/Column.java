package com.boyce.crud.template.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @description
 * @author Boyce
 * @date 2020/4/13 19:15
 * @version V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
}
