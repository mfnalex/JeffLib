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

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Internet and network related methodds
 */
@UtilityClass
public class NetUtils {

    /**
     * Downloads a URL and returns the response as String list asynchronously.
     */
    @NotNull
    public static CompletableFuture<List<String>> downloadToStringListAsync(final String url) {
        final CompletableFuture<List<String>> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(JeffLib.getPlugin(), () -> {
            try {
                final List<String> result = downloadToStringList(url);
                future.complete(result);
            } catch (final IOException exception) {
                future.completeExceptionally(exception);
            }
        });
        return future;
    }

    /**
     * Downloads a URL and returns the response as String list. Blocks the main thread.
     */
    @NotNull
    public static List<String> downloadToStringList(final String url) throws IOException {

        final HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
        //noinspection HardcodedFileSeparator
        httpConnection.addRequestProperty("User-Agent", JeffLib.getPlugin().getName() + "/" + JeffLib.getPlugin().getDescription().getVersion());
        try (final InputStreamReader input = new InputStreamReader(httpConnection.getInputStream()); final BufferedReader reader = new BufferedReader(input)) {
            final Stream<String> result = reader.lines();
            return result.collect(Collectors.toList());
        }
    }

    /**
     * Returns the public IP address of the server, or null if it couldn't find it out. This method uses a blocking
     * request to <a href="http://checkip.amazonaws.com">http://checkip.amazonaws.com</a>
     */
    @Nullable
    public static String getIp() {
        try {
            final List<String> answer = downloadToStringList("http://checkip.amazonaws.com");
            if (answer.isEmpty()) return null;
            return answer.get(0);
        } catch (final IOException e) {
            return null;
        }
    }

}
