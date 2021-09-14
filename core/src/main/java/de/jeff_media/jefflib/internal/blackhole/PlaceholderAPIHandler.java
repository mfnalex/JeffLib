package de.jeff_media.jefflib.internal.blackhole;

import de.jeff_media.jefflib.JeffLib;
import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import de.jeff_media.jefflib.internal.InternalOnly;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
@InternalOnly
@Deprecated
public final class PlaceholderAPIHandler {

    private static AnonymousPlaceholderExpansion expansion;

    private static void init() {
        if(JeffLib.getPlugin()==null) {
            throw new JeffLibNotInitializedException();
        }

        if(expansion == null) {
            expansion = new AnonymousPlaceholderExpansion();
            expansion.register();
        }
    }

    public static void register(@NotNull final BiFunction<OfflinePlayer, String, String> function) {
        init();
        expansion.biFunctions.add(function);
    }

    public static void register(@NotNull final String placeholder, @NotNull final Function<OfflinePlayer, String> function) {
        init();
        expansion.functions.put(placeholder, function);
    }

    private static final class AnonymousPlaceholderExpansion extends PlaceholderExpansion {

        Map<String,Function<OfflinePlayer,String>> functions = new HashMap<>();
        Collection<BiFunction<OfflinePlayer, String, String>> biFunctions = new ArrayList<>();

        @Override
        public @NotNull String getIdentifier() {
            return JeffLib.getPlugin().getName().toLowerCase(Locale.ROOT);
        }

        @Override
        public @NotNull String getAuthor() {
            return JeffLib.getPlugin().getDescription().getAuthors().isEmpty() ? "" : JeffLib.getPlugin().getDescription().getAuthors().get(0);
        }

        @Override
        public String onRequest(final OfflinePlayer player, @NotNull final String params) {
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
        public @NotNull String getVersion() {
            return JeffLib.getPlugin().getDescription().getVersion();
        }
    }
}
