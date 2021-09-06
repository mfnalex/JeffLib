package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class PlaceholderAPIUtils {

    public static boolean registerPlaceholder(@NotNull final String identifier, @NotNull final BiFunction<OfflinePlayer, String, String> function) {
        if(JeffLib.getPlugin()==null) {
            throw new JeffLibNotInitializedException();
        }

        return new AnonymousPlaceholderExpansion(identifier,function).register();
    }

    private static final class AnonymousPlaceholderExpansion extends PlaceholderExpansion {
        private final String identifier;
        private final BiFunction<OfflinePlayer, String, String> function;

        private AnonymousPlaceholderExpansion(final String identifier, final BiFunction<OfflinePlayer, String, String> function) {
            this.identifier = identifier;
            this.function = function;
        }

        @Override
        public @NotNull String getIdentifier() {
            return identifier;
        }

        @Override
        public @NotNull String getAuthor() {
            return "";
        }

        @Override
        public String onRequest(final OfflinePlayer player, @NotNull final String params) {
            return function.apply(player, params);
        }

        @Override
        public @NotNull String getVersion() {
            return JeffLib.getPlugin().getDescription().getVersion();
        }
    }
}
