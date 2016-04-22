package com.jm.android.jmlauncher.initializer.annonation;

/**
 * Created by chaoranf@jumei.com on 16/3/16.
 * 与异步对应的一个注解
 */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMSync {
}
