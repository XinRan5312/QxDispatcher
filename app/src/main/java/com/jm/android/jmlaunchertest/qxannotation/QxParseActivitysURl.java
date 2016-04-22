package com.jm.android.jmlaunchertest.qxannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by qixinh on 16/4/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QxParseActivitysURl {
    String key() default "";
    String url() default "";
}
