/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.exceptions;

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.JeffLib;
import org.bukkit.plugin.Plugin;

/**
 * Gets thrown when a plugin attempts to use an API method that needs initialization first, without having called {@link JeffLib#init(Plugin)} first.
 *
 * @deprecated JeffLib 9.0.0+ automatically gets the plugin's instance
 */
@DoNotRename
@Deprecated
public final class JeffLibNotInitializedException extends IllegalStateException {

    public JeffLibNotInitializedException() {
        super("JeffLib hasn't been initialized. Use JeffLib#init before using this method.");
    }

    public static void check() throws JeffLibNotInitializedException {
        if (JeffLib.getPlugin() == null) {
            throw new JeffLibNotInitializedException();
        }
    }

}
