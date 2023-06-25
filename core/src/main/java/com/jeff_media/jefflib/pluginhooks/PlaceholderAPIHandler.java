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

package com.jeff_media.jefflib.pluginhooks;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.internal.annotations.Internal;
import com.jeff_media.jefflib.internal.annotations.RequiresPlugin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * PlaceholderAPI related methods
 *
 * @plugin PlaceholderAPI
 * @internal
 */
@UtilityClass
@Internal
@RequiresPlugin("PlaceholderAPI")
public final class PlaceholderAPIHandler {

    private static AnonymousPlaceholderExpansion expansion;

    public static void register(@NotNull final BiFunction<OfflinePlayer, String, String> function) {
        init();
        expansion.biFunctions.add(function);
    }

    private static void init() {
        if (expansion == null) {
            expansion = new AnonymousPlaceholderExpansion();
            expansion.register();
        }
    }

    public static void register(@NotNull final String placeholder, @NotNull final Function<OfflinePlayer, String> function) {
        init();
        expansion.functions.put(placeholder, function);
    }

    private static final class AnonymousPlaceholderExpansion extends PlaceholderExpansion {

        private final Map<String, Function<OfflinePlayer, String>> functions = new HashMap<>();
        private final Collection<BiFunction<OfflinePlayer, String, String>> biFunctions = new ArrayList<>();

        @Override
        @NotNull
        public String getIdentifier() {
            return JeffLib.getPlugin().getName().toLowerCase(Locale.ROOT);
        }

        @Override
        @NotNull
        public String getAuthor() {
            return JeffLib.getPlugin().getDescription().getAuthors().isEmpty() ? "" : JeffLib.getPlugin().getDescription().getAuthors().get(0);
        }

        @Override
        @NotNull
        public String getVersion() {
            return JeffLib.getPlugin().getDescription().getVersion();
        }

        @Override
        public boolean persist() {
            return true;
        }

        @Override
        public String onRequest(final OfflinePlayer player, @NotNull final String params) {
            final Function<OfflinePlayer, String> function = functions.get(params);
            if (function != null) {
                return function.apply(player);
            }
            for (final BiFunction<OfflinePlayer, String, String> biFunction : biFunctions) {
                final String result = biFunction.apply(player, params);
                if (result != null) return result;
            }
            return null;
        }
    }
}
