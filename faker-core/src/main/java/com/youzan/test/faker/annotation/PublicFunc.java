package com.youzan.test.faker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by libaixian on 16/8/4.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface PublicFunc {
    String name() default "";
}
