package de.jeff_media.jefflib;

import com.google.common.base.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * Data class to wrap all information needed to play a sound
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public final class ParticleData {
    private Particle particle;
    private int amount = 1;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double speed;

    public static ParticleData fromConfigurationSection(@NotNull final ConfigurationSection config, @Nullable String prefix) {

        if(prefix == null) prefix = "";
        final String particleName = config.getString(prefix + "type");
        if(particleName == null || particleName.isEmpty()) {
            throw new IllegalArgumentException("No particle type defined");
        }

        Particle particle = Enums.getIfPresent(Particle.class, particleName.toUpperCase(Locale.ROOT)).orNull();
        if(particle == null) {
            throw new IllegalArgumentException("Unknown particle type: " + particleName);
        }

        int amount = 1;
        if(config.isInt(prefix + "amount")) {
            amount = config.getInt(prefix+"amount");
        }
        double offsetX = 0;
        if(config.isDouble(prefix + "offset-x")) {
            offsetX = config.getDouble(prefix+"offset-x");
        }
        double offsetY = 0;
        if(config.isDouble(prefix + "offset-y")) {
            offsetY = config.getDouble(prefix+"offset-y");
        }
        double offsetZ = 0;
        if(config.isDouble(prefix + "offset-z")) {
            offsetZ = config.getDouble(prefix+"offset-z");
        }
        double speed = 0;
        if(config.isDouble(prefix +  "speed")) {
            speed = config.getDouble(prefix + speed);
        }

        return new ParticleData(particle,amount,offsetX,offsetY,offsetZ,speed);
    }

    public void playToPlayer(@NotNull final Player player, @NotNull final Location location) {
        player.spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ, speed);
    }

    public void playToWorld(@NotNull final Location location) {
        location.getWorld().spawnParticle(particle,location,amount, offsetX, offsetY, offsetZ, speed);
    }

}

