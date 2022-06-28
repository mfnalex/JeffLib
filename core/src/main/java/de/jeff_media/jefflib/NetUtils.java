package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public final class NetUtils {

    /**
     * Downloads a URL and returns the response as String list. Blocks the main thread.
     */
    @Nonnull
    public static List<String> downloadToStringList(final String url) throws IOException {

        final HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
        //noinspection HardcodedFileSeparator
        httpConnection.addRequestProperty("User-Agent", JeffLib.getPlugin().getName() + "/" + JeffLib.getPlugin().getDescription().getVersion());
        try (
                final InputStreamReader input = new InputStreamReader(httpConnection.getInputStream());
                final BufferedReader reader = new BufferedReader(input)
        ) {
            final Stream<String> result = reader.lines();
            return result.collect(Collectors.toList());
        }
    }

    /**
     * Downloads a URL and returns the response as String list asynchronously.
     */
    @Nonnull
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
     * Returns the public IP address of the server, or null if it couldn't find it out. Blocks the main thread.
     */
    @Nullable
    public static String getIp() {
        try {
            final List<String> answer = downloadToStringList("https://ifconfig.me/ip");
            if(answer.isEmpty()) return null;
            return answer.get(0);
        } catch (final IOException e) {
            return null;
        }
    }

}
