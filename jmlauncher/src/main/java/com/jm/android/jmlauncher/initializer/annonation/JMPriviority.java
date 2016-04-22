package com.jm.android.jmlauncher.initializer.annonation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示同步初始化时候的优先级。谁小谁先调用。不能和Async一起使用。
 * <p/>
 * Created by sunxiao on 16/2/29.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMPriviority {
    eJMPriviority value() default eJMPriviority.ePriviorityNormal;
}
