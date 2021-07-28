package de.jeff_media.jefflib;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

/**
 * Data class to wrap all information needed to play a sound
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SoundData {
    private final Sound sound;
    private float volume = 1;
    private float pitch = 1;
    private float pitchRange = 0;
    private SoundCategory soundCategory = SoundCategory.MASTER;

    private float getFinalPitch() {
        return (float) (pitch - (pitchRange/2) + JeffLib.getThreadLocalRandom().nextDouble(0,pitchRange));
    }

    public void playToPlayer(Player player) {
        player.playSound(player.getLocation(),sound,soundCategory,volume,getFinalPitch());
    }

    public void playToPlayer(Player player, Location location) {
        player.playSound(location,sound,soundCategory,volume,getFinalPitch());
    }

    public void playToWorld(Location location) {
        location.getWorld().playSound(location,sound,soundCategory,volume,getFinalPitch());
    }

}

