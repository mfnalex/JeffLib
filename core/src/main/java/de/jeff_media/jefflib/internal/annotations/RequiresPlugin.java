package de.jeff_media.jefflib.internal.annotations;

import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that the annotated method or class requires the given plugin to be installed
 */
@Documented
@TypeQualifierNickname
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPlugin {
    String value();
}
