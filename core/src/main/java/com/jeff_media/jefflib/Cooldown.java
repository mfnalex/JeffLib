package com.jeff_media.jefflib;

import org.bukkit.entity.Entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

/**
 * A cooldown tracker for all kinds of objects. One instance should be used per kind of cooldown. For example if you have
 * two commands, and both should have a cooldown that's not related to each other, you should create two instances of this.
 */
public class Cooldown {

    private static final LinkedHashMap<TimeUnit, LongSupplier> DEFAULT_TIMEUNIT_SUPPLIERS = new LinkedHashMap<>();

    static {
        DEFAULT_TIMEUNIT_SUPPLIERS.put(TimeUnit.NANOSECONDS, System::nanoTime);
        DEFAULT_TIMEUNIT_SUPPLIERS.put(TimeUnit.MICROSECONDS, () -> System.nanoTime() / 1000L);
        DEFAULT_TIMEUNIT_SUPPLIERS.put(TimeUnit.MILLISECONDS, System::currentTimeMillis);
        DEFAULT_TIMEUNIT_SUPPLIERS.put(TimeUnit.SECONDS, () -> System.currentTimeMillis() / 1000L);
        DEFAULT_TIMEUNIT_SUPPLIERS.put(TimeUnit.MINUTES, () -> System.currentTimeMillis() / 1000L / 60L);
        DEFAULT_TIMEUNIT_SUPPLIERS.put(TimeUnit.HOURS, () -> System.currentTimeMillis() / 1000L / 60L / 60L);
        DEFAULT_TIMEUNIT_SUPPLIERS.put(TimeUnit.DAYS, () -> System.currentTimeMillis() / 1000L / 60L / 60L / 24L);
    }

    private final Map<Object, Long> cooldowns = new ConcurrentHashMap<>();
    private final LongSupplier timeSupplier;
    private final TimeUnit precision;

    /**
     * Creates a new cooldown tracker with the default precision (milliseconds)
     */
    public Cooldown() {
        this(TimeUnit.MILLISECONDS, System::currentTimeMillis);
    }

    /**
     * Creates a new cooldown tracker with the given precision
     */
    public Cooldown(final TimeUnit precision) {
        final LongSupplier supplier = DEFAULT_TIMEUNIT_SUPPLIERS.get(precision);
        if(supplier == null) {
            throw new IllegalArgumentException("Unsupported TimeUnit: " + precision.name() + ". Must be one of " + DEFAULT_TIMEUNIT_SUPPLIERS.keySet().stream().map(Enum::name).collect(Collectors.joining(", ")));
        }
        this.precision = precision;
        this.timeSupplier = supplier;
    }

    /**
     * Creates a new cooldown tracker with the given precision and time supplier
     */
    public Cooldown(final TimeUnit precision, final LongSupplier timeSupplier) {
        this.precision = precision;
        this.timeSupplier = timeSupplier;
    }

    public void removeCooldown(final Object object) {
        cooldowns.remove(getUid(object));
    }

    private void clearOldEntries() {
        final long now = getTime();
        cooldowns.entrySet().removeIf(entry -> now >= entry.getValue());
    }

    /**
     * Gets the precicion of this cooldown tracker
     */
    public TimeUnit getPrecision() {
        return precision;
    }

    /**
     * Sets the cooldown for this object
     */
    public void setCooldown(final Object object, final long duration, final TimeUnit timeUnit) {
        clearOldEntries();
        cooldowns.put(getUid(object),getTime() + precision.convert(duration, timeUnit));
    }

    /**
     * Gets when the cooldown for this object expires, or 0 if there is no cooldown or if it has expired.
     */
    public long getCooldownEnd(final Object object) {
        clearOldEntries();
        final Object uid = getUid(object);
        final long end = cooldowns.getOrDefault(uid,0L);
        if(end == 0) return 0;
        if(getTime() > end) {
            return 0;
        }
        return end;
    }

    /**
     * Gets whether this object is on cooldown
     */
    public boolean hasCooldown(final Object object) {
        clearOldEntries();
        final Object uid = getUid(object);
        final long end = getCooldownEnd(uid);
        return end > getTime();
    }

    /**
     * Gets the remaining duration until the cooldown for this object requires, in the given {@link TimeUnit}, or 0 if there is no cooldown for this object or if it has exipred.
     */
    public long getCooldownRemaining(final Object object, final TimeUnit timeUnit) {
        clearOldEntries();
        final long endTime = getCooldownEnd(object);
        if(endTime == 0) return 0;
        return timeUnit.convert(endTime - getTime(),precision);
    }

    private long getTime() {
        return timeSupplier.getAsLong();
    }

    private static Object getUid(final Object object) {
        if(object instanceof Entity) {
            return ( (Entity) object ).getUniqueId();
        }
        return object;
    }

}
