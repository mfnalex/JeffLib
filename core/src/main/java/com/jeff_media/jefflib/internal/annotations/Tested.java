package com.jeff_media.jefflib.internal.annotations;

import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method has been tested in the given MC versions
 */
@Documented
@TypeQualifierNickname
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Tested {
    String[] value();
}
