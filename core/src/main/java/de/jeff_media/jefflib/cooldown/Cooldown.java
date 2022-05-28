package de.jeff_media.jefflib.cooldown;

import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

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

    public Cooldown() {
        this(TimeUnit.MILLISECONDS, System::currentTimeMillis);
    }

    public Cooldown(TimeUnit precision) {
        LongSupplier supplier = DEFAULT_TIMEUNIT_SUPPLIERS.get(precision);
        if(supplier == null) {
            throw new IllegalArgumentException("Unsupported TimeUnit: " + precision.name() + ". Must be one of " + DEFAULT_TIMEUNIT_SUPPLIERS.keySet().stream().map(Enum::name).collect(Collectors.joining(", ")));
        }
        this.precision = precision;
        this.timeSupplier = supplier;
    }

    private Cooldown(TimeUnit precision, LongSupplier timeSupplier) {
        this.precision = precision;
        this.timeSupplier = timeSupplier;
    }

    public boolean clearOldEntries() {
        long now = getTime();
        return cooldowns.entrySet().removeIf(entry -> now >= entry.getValue());
    }

    public TimeUnit getPrecision() {
        return precision;
    }

    public void setCooldown(Object object, long duration, TimeUnit timeUnit) {
        cooldowns.put(getUid(object),getTime() + precision.convert(duration, timeUnit));
    }

    public long getCooldownEnd(Object object) {
        Object uid = getUid(object);
        long end = cooldowns.getOrDefault(uid,0L);
        if(end == 0) return 0;
        if(getTime() > end) {
            cooldowns.remove(uid);
            return 0;
        }
        return end;
    }

    public boolean hasCooldown(Object object) {
        Object uid = getUid(object);
        long end = getCooldownEnd(uid);
        return end > getTime();
    }

    public long getCooldownRemaining(Object object, TimeUnit timeUnit) {
        long endTime = getCooldownEnd(object);
        if(endTime == 0) return 0;
        return timeUnit.convert(endTime - getTime(),precision);
    }

    private long getTime() {
        return timeSupplier.getAsLong();
    }

    private static Object getUid(Object object) {
        if(object instanceof Entity) {
            return ( (Entity) object ).getUniqueId();
        }
        return object;
    }

}
