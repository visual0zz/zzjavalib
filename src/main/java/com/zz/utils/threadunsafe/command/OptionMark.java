package com.zz.utils.threadunsafe.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OptionMark {
    String value()default "";//选项的全名
    String shortName() default "";//选项的省略名
    String comment()default "";
}
