package de.jeff_media.jefflib;

import com.google.common.base.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * Data class to wrap all information needed to play a sound
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public final class SoundData {

    /**
     *
     */
    private final Sound sound;
    private float volume = 1;
    private float pitch = 1;
    private float pitchVariant;
    private SoundCategory soundCategory = SoundCategory.MASTER;

    public static SoundData fromConfigurationSection(@Nonnull final ConfigurationSection config, @Nullable String prefix) {
        if(prefix == null) prefix = "";
        final String soundName = config.getString(prefix + "effect");
        if(soundName == null || soundName.isEmpty()) {
            throw new IllegalArgumentException("No sound effect defined");
        }
        final Sound sound = Enums.getIfPresent(Sound.class, soundName.toUpperCase(Locale.ROOT)).orNull();
        if(sound == null) {
            throw new IllegalArgumentException("Unknown sound effect: " + soundName);
        }
        final float volume = (float) config.getDouble(prefix + "volume", 1.0D);
        final float pitch = (float) config.getDouble(prefix + "pitch", 1.0D);
        final float pitchVariant = (float) config.getDouble(prefix + "pitch-variant", 1.0D);
        final String soundCategoryName = config.getString(prefix + "sound-category",SoundCategory.MASTER.name()).toUpperCase(Locale.ROOT);
        final SoundCategory soundCategory = Enums.getIfPresent(SoundCategory.class, soundCategoryName).orNull();
        if(soundCategory == null) {
            throw new IllegalArgumentException("Unknown sound category: " + soundCategoryName);
        }

        return new SoundData(sound, volume, pitch, pitchVariant, soundCategory);
    }

    private float getFinalPitch() {
        return (float) (pitch - (pitchVariant / 2) + JeffLib.getThreadLocalRandom().nextDouble(0, pitchVariant));
    }

    /**
     * Plays the sound only to the given player
     *
     * @param player Player
     */
    public void playToPlayer(final Player player) {
        player.playSound(player.getLocation(), sound, soundCategory, volume, getFinalPitch());
    }

    /**
     * Plays the sound only to the given player, at the given location
     *
     * @param player   Player
     * @param location Location
     */
    public void playToPlayer(final Player player, final Location location) {
        player.playSound(location, sound, soundCategory, volume, getFinalPitch());
    }

    /**
     * Plays the sound to all players in the world, at the given location
     *
     * @param location Location
     */
    public void playToWorld(final Location location) {
        Objects.requireNonNull(location.getWorld()).playSound(location, sound, soundCategory, volume, getFinalPitch());
    }

}

