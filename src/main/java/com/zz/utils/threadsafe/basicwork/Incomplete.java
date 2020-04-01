package com.zz.utils.threadsafe.basicwork;

import java.lang.annotation.*;


/**
 * 标记代码没有写完，还不能正常使用
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
public @interface Incomplete {
    String value()default "";
}
