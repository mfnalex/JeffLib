package com.jeff_media.jefflib.pluginhooks;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.internal.annotations.Internal;
import com.jeff_media.jefflib.internal.annotations.RequiresPlugin;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import javax.annotation.Nonnull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * PlaceholderAPI related methods
 * @plugin PlaceholderAPI
 * @internal
 */
@UtilityClass
@Internal
@RequiresPlugin("PlaceholderAPI")
public final class PlaceholderAPIHandler {

    private static AnonymousPlaceholderExpansion expansion;

    private static void init() {
        if(expansion == null) {
            expansion = new AnonymousPlaceholderExpansion();
            expansion.register();
        }
    }

    public static void register(@Nonnull final BiFunction<OfflinePlayer, String, String> function) {
        init();
        expansion.biFunctions.add(function);
    }

    public static void register(@Nonnull final String placeholder, @Nonnull final Function<OfflinePlayer, String> function) {
        init();
        expansion.functions.put(placeholder, function);
    }

    private static final class AnonymousPlaceholderExpansion extends PlaceholderExpansion {

        @Override
        public boolean persist() {
            return true;
        }

        private final Map<String,Function<OfflinePlayer,String>> functions = new HashMap<>();
        private final Collection<BiFunction<OfflinePlayer, String, String>> biFunctions = new ArrayList<>();

        @Override
        @Nonnull
        public String getIdentifier() {
            return JeffLib.getPlugin().getName().toLowerCase(Locale.ROOT);
        }

        @Override
        @Nonnull
        public String getAuthor() {
            return JeffLib.getPlugin().getDescription().getAuthors().isEmpty() ? "" : JeffLib.getPlugin().getDescription().getAuthors().get(0);
        }

        @Override
        public String onRequest(final OfflinePlayer player, @Nonnull final String params) {
            final Function<OfflinePlayer, String> function = functions.get(params);
            if(function != null) {
                return function.apply(player);
            }
            for (final BiFunction<OfflinePlayer, String, String> biFunction : biFunctions) {
                final String result = biFunction.apply(player, params);
                if(result != null) return result;
            }
            return null;
        }

        @Override
        @Nonnull
        public String getVersion() {
            return JeffLib.getPlugin().getDescription().getVersion();
        }
    }
}
