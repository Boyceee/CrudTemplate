package com.boyce.crud.template.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/4/13 18:28
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String value();
}
