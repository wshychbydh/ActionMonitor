package com.plugin.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cool on 2018/4/4.
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Ignore {
}
