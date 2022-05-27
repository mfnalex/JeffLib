package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.internal.cherokee.NotImplementedException;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Cooldown {

    private final Map<UUID,Long> lastUsages = new HashMap<>();
    private final long minimumDelay;
    private final TimeUnit timeUnit;

    /**
     * Creates a new Cooldown that doesn't have any default cooldown durations. You will always have to provide the
     * delay, so you can use {@link #hasCooldown(OfflinePlayer, long, TimeUnit)} but not {@link #hasCooldown(OfflinePlayer}
     */
    public Cooldown() {
        this.minimumDelay = -1;
        this.timeUnit = null;
    }

    /**
     * Creates a new Cooldown that has a default cooldown duration. You can also check for custom delays, so you can use
     * both {@link #hasCooldown(OfflinePlayer, long, TimeUnit)} and {@link #hasCooldown(OfflinePlayer}
     * @param minimumDelay Default minimum delay
     * @param timeUnit {@link TimeUnit} of the given default minimum delay
     */
    public Cooldown(long minimumDelay, @NotNull TimeUnit timeUnit) {
        if(minimumDelay <= 0) {
            throw new IllegalArgumentException("minimumDelay cannot be smaller than 1");
        }
        this.minimumDelay = minimumDelay;
        if(timeUnit == null) {
            throw new IllegalArgumentException("timeUnit cannot be null");
        }
        this.timeUnit = timeUnit;
        throw new NotImplementedException();
    }

    /**
     * Sets the cooldown starting period to this player to "now"
     */
    public void setCooldownNow(OfflinePlayer player) {
        lastUsages.put(player.getUniqueId(), System.currentTimeMillis());
    }

    /**
     * Checks if at least the given delay has occurred for this player since their cooldown was last set.
     * @return True when the player is still in cooldown, false if the minimum delay has occured / cooldown has expired
     */
    public boolean hasCooldown(OfflinePlayer player, long minimumDelay, TimeUnit timeUnit) {
        long lastValue = lastUsages.computeIfAbsent(player.getUniqueId(), __ -> 0L);
        long minDelay = TimeUnit.MILLISECONDS.convert(minimumDelay, timeUnit);
        return System.currentTimeMillis() - lastValue < minDelay;
    }

    public boolean hasDefaultDelay() {
        return minimumDelay > 0 && timeUnit != null;
    }

    public @Nullable Pair<Long, TimeUnit> getDefaultDelay() {
        if(!hasDefaultDelay()) return null;
        return new Pair<>(minimumDelay, timeUnit);
    }

    public boolean hasCooldown(OfflinePlayer player) {
        if(!hasDefaultDelay()) throw new UnsupportedOperationException("No delay and no default delay provided");
        throw new NotImplementedException();
    }

    public long getRemainingCooldown(OfflinePlayer player, TimeUnit timeUnit) {
        throw new NotImplementedException();
    }
}
