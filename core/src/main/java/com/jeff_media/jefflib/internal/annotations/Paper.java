package com.jeff_media.jefflib.internal.annotations;

import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that a method or class is only available on Paper or Paper forks
 */
@Documented
@TypeQualifierNickname
@Retention(RetentionPolicy.RUNTIME)
public @interface Paper {
}
