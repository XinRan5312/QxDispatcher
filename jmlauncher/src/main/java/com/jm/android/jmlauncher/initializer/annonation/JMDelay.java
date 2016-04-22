package com.jm.android.jmlauncher.initializer.annonation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示最不重要的初始化，在 Looper 里消息为空后调用。
 * <p/>
 * JMPriviority有了，为什么还有这个？没人会把自己的模块定位为最低优先级的，所以，加入这个，表示最最低的优先级
 * <p/>
 * Created by sunxiao on 16/2/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface JMDelay {
}
