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

import com.google.common.base.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

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

    public static ParticleData fromConfigurationSection(@Nonnull final ConfigurationSection config, @Nullable String prefix) {

        if (prefix == null) prefix = "";
        final String particleName = config.getString(prefix + "type");
        if (particleName == null || particleName.isEmpty()) {
            throw new IllegalArgumentException("No particle type defined");
        }

        final Particle particle = Enums.getIfPresent(Particle.class, particleName.toUpperCase(Locale.ROOT)).orNull();
        if (particle == null) {
            throw new IllegalArgumentException("Unknown particle type: " + particleName);
        }

        int amount = 1;
        if (config.isInt(prefix + "amount")) {
            amount = config.getInt(prefix + "amount");
        }
        double offsetX = 0;
        if (config.isDouble(prefix + "offset-x")) {
            offsetX = config.getDouble(prefix + "offset-x");
        }
        double offsetY = 0;
        if (config.isDouble(prefix + "offset-y")) {
            offsetY = config.getDouble(prefix + "offset-y");
        }
        double offsetZ = 0;
        if (config.isDouble(prefix + "offset-z")) {
            offsetZ = config.getDouble(prefix + "offset-z");
        }
        double speed = 0;
        if (config.isDouble(prefix + "speed")) {
            speed = config.getDouble(prefix + speed);
        }

        return new ParticleData(particle, amount, offsetX, offsetY, offsetZ, speed);
    }

    public void playToPlayer(@Nonnull final Player player, @Nonnull final Location location) {
        player.spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ, speed);
    }

    public void playToWorld(@Nonnull final Location location) {
        location.getWorld().spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ, speed);
    }

}

