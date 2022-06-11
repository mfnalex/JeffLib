package de.jeff_media.jefflib.internal.annotations;

import de.jeff_media.jefflib.JeffLib;

import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that the annotated method uses NMS and will only work in a supported Minecraft version after calling {@link JeffLib#enableNMS()}
 */
@Documented
@TypeQualifierNickname
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresNMS {
}
