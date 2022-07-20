package com.jeff_media.jefflib.internal.annotations;

import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that the annotated method or class is meant to be used by JeffLib internally only.
 */
@Documented
@TypeQualifierNickname
@Retention(RetentionPolicy.RUNTIME)
public @interface Internal {
}
