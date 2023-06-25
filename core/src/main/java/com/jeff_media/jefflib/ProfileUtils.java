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

import com.jeff_media.jefflib.internal.annotations.NMS;
import java.io.File;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class ProfileUtils {

    /**
     * Turns a String into a UUID, whether it contains dashes or not.
     */
    public static UUID getUUIDFromString(@NotNull final String string) {
        if (string.length() == 36) return UUID.fromString(string);
        if (string.length() == 32) return fromStringWithoutDashes(string);
        throw new IllegalArgumentException("Not a valid UUID.");
    }

    private static UUID fromStringWithoutDashes(@NotNull final String string) {
        return UUID.fromString(string.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
    }

    public static boolean isValidUUID(@NotNull final String string) {
        return string.replace("-", "").matches("^\\p{XDigit}{32}$");
    }

    public static boolean isValidAccountName(@NotNull final String name) {
        return name.matches("^\\w{3,16}$");
    }

    /**
     * Returns the player's data (.dat) file
     *
     * @nms
     */
    @NMS
    @NotNull
    public static File getPlayerDataFile(UUID uuid) {
        File playerDataFolder = new File(WorldUtils.getDefaultWorld().getWorldFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
        return new File(playerDataFolder, uuid.toString() + ".dat");
    }
}
