package com.jm.android.jmlauncher.initializer.annonation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个 JMFlow 表示一个线程，可以和Priority共同使用表示两个有前后依赖关系的初始化
 * <p/>
 * Created by sunxiao on 16/2/29.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JMFlow {
}
