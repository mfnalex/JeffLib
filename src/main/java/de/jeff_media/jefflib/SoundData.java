/*
package de.jeff_media.jefflib;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

@Data
public class SoundData {
    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final float pitchRange;
    private final SoundCategory soundCategory;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final float finalPitch = (float) (pitch - (pitchRange/2) + random.nextDouble(0,pitchRange));

    public void playToPlayer(Player player) {
        player.playSound(player.getLocation(),sound,soundCategory,volume,finalPitch);
    }

    public void playToPlayer(Player player, Location location) {
        player.playSound(location,sound,soundCategory,volume,finalPitch);
    }

    public void playToWorld(Location location) {
        location.getWorld().playSound(location,sound,soundCategory,volume,finalPitch);
    }

}
*/
