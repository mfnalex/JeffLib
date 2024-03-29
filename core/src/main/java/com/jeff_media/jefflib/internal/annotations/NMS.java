/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.internal.annotations;

import com.jeff_media.jefflib.JeffLib;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifierNickname;

/**
 * Indicates that the annotated method uses NMS and will only work in a supported Minecraft version after calling {@link JeffLib#enableNMS()}
 */
@Documented
@TypeQualifierNickname
@Retention(RetentionPolicy.CLASS)
public @interface NMS {
    /**
     * The minimum version of Minecraft that this feature is supported on
     */
    String value() default "";

}
