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

package com.jeff_media.jefflib;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.bukkit.Keyed;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for working with {@link Tag}s
 */
public final class TagUtils {

    private static final Map<String, Tag<? extends Keyed>> TAGS = new HashMap<>();

    static {
        for (Field field : Tag.class.getFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }
            if (field.getType() != Tag.class) {
                continue;
            }
            try {
                Tag<? extends Keyed> tag = (Tag<? extends Keyed>) field.get(null);
                TAGS.put(tag.getKey().getKey(), tag);
            } catch (IllegalAccessException ignored) {

            }
        }
    }

    /**
     * Gets a tag by its name as declared in {@link Tag}
     *
     * @param name Name of the tag
     * @return Tag or null if not found
     */
    @Nullable
    public static Tag<? extends Keyed> getTag(@NotNull String name) {
        return TAGS.get(name.toUpperCase());
    }

}
