package com.jeff_media.jefflib.data;

import com.google.common.base.Enums;
import com.jeff_media.jefflib.HologramManager;
import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.TextUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a Hologram
 */
public class Hologram implements ConfigurationSerializable {

    private static final double LINE_OFFSET_DEFAULT = -0.25;
    private static final boolean VISIBLE_FOR_ANYONE_DEFAULT = true;
    private static final double VISIBILITY_RADIUS_DEFAULT = 64;
    @Getter
    private final Type type;
    @Getter
    @Nonnull
    private final List<OfflinePlayer> players = new ArrayList<>();
    @Getter
    @Nonnull
    private final List<Object> entities = new ArrayList<>();
    private Integer task = null;
    @Getter
    @Setter
    @Nullable
    private OfflinePlayer player;
    @Getter
    @Setter
    @Nonnull
    private Location location;
    @Getter
    @Setter
    private double lineOffset = LINE_OFFSET_DEFAULT;
    @Getter
    @Setter
    @Nonnull
    private List<String> lines = new ArrayList<>();
    @Getter
    @Setter
    private boolean isVisibleForAnyone = VISIBLE_FOR_ANYONE_DEFAULT;
    @Getter
    @Setter
    private double visibilityRadius = VISIBILITY_RADIUS_DEFAULT;

    public Hologram(final Type type) {
        this.type = type;
    }

    public static Hologram deserialize(@Nonnull final Map<String, Object> map) {
        final Type type = Enums.getIfPresent(Type.class, (String) map.getOrDefault(Keys.TYPE, "ARMORSTAND")).or(Type.ARMORSTAND);
        final Hologram hologram = new Hologram(type);
        hologram.setLineOffset((double) map.getOrDefault(Keys.LINE_OFFSET, LINE_OFFSET_DEFAULT));
        //noinspection unchecked
        hologram.getLines().addAll((List<String>) map.getOrDefault(Keys.LINES, new ArrayList<String>()));
        hologram.setVisibleForAnyone((boolean) map.getOrDefault(Keys.IS_VISIBlE_FOR_ANYONE, VISIBLE_FOR_ANYONE_DEFAULT));
        hologram.setVisibilityRadius((double) map.getOrDefault(Keys.VISIBILITY_RADIUS, VISIBILITY_RADIUS_DEFAULT));
        hologram.setLocation((Location) map.get(Keys.LOCATION));
        //noinspection unchecked
        final List<String> offlinePlayerUUIDs = (List<String>) map.getOrDefault(Keys.PLAYERS, new ArrayList<String>());
        offlinePlayerUUIDs.forEach(entry -> {
            try {
                final UUID uuid = UUID.fromString(entry);
                final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                hologram.getPlayers().add(player);
            } catch (final Throwable ignored) {
            }
        });
        return hologram;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, location, lineOffset, lines, isVisibleForAnyone, visibilityRadius, players, entities);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Hologram hologram = (Hologram) o;
        return Double.compare(hologram.lineOffset, lineOffset) == 0 && isVisibleForAnyone == hologram.isVisibleForAnyone && Double.compare(hologram.visibilityRadius, visibilityRadius) == 0 && type == hologram.type && location.equals(hologram.location) && lines.equals(hologram.lines) && players.equals(hologram.players) && entities.equals(hologram.entities);
    }

    @Override
    public String toString() {
        return "Hologram{" + "type=" + type + ", location=" + location + ", lineOffset=" + lineOffset + ", lines=" + lines + ", isVisibleForAnyone=" + isVisibleForAnyone + ", visibilityRadius=" + visibilityRadius + ", players=" + players + ", entities=" + entities + '}';
    }

    public void update(final int ticks) {
        if (task != null) {
            Bukkit.getScheduler().cancelTask(task);
        }
        if (ticks > 0) {
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(JeffLib.getPlugin(), this::update, ticks, ticks);
        }
    }

    @SneakyThrows
    public void update() {
        if (lines.size() == entities.size()) {
            for (int i = 0; i < lines.size(); i++) {
                JeffLib.getNMSHandler().changeNMSEntityName(entities.get(i), format().get(i));
            }
        } else {
            for (final Object entity : entities) {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    JeffLib.getNMSHandler().hideEntityFromPlayer(entity, player);
                }
            }
            create();
        }
    }

    private List<String> format() {
        //System.out.println("Formatting using player " + player);
        return TextUtils.format(lines, player);
    }

    public void create() {
        Location current = location.clone();
        for (final String line : format()) {
            //System.out.println("Creating hologram line: " + line);
            final Object entity = JeffLib.getNMSHandler().createHologram(current, line, type);
            entities.add(entity);
            //System.out.println(entity.toString());
            current = current.add(0, lineOffset, 0);
        }
        HologramManager.getHolograms().add(this);
    }

    @Nonnull
    public Map<String, Object> serialize() {
        final Map<String, Object> map = new HashMap<>();
        map.put(Keys.LINE_OFFSET, lineOffset);
        map.put(Keys.LINES, lines);
        map.put(Keys.IS_VISIBlE_FOR_ANYONE, isVisibleForAnyone);
        map.put(Keys.VISIBILITY_RADIUS, visibilityRadius);
        map.put(Keys.PLAYERS, new ArrayList<>(players.stream().map(player -> player.getUniqueId().toString()).collect(Collectors.toList())));
        map.put(Keys.LOCATION, location);
        return map;
    }

    public enum Type {
        ARMORSTAND, EFFECTCLOUD
    }

    private static final class Keys {
        static final String TYPE = "type";
        static final String LINE_OFFSET = "line-offset";
        static final String LINES = "lines";
        static final String IS_VISIBlE_FOR_ANYONE = "is-visible-for-anyone";
        static final String VISIBILITY_RADIUS = "visibility-radius";
        static final String PLAYERS = "players";
        static final String LOCATION = "location";
    }

}
