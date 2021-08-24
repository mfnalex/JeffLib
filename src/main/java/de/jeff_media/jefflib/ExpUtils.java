package de.jeff_media.jefflib;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;

public class ExpUtils {

    public static int getTotalXPRequiredForLevel(final int targetLevel) {
        if (targetLevel <= 16) return sqrt(targetLevel) + (6 * targetLevel);
        if (targetLevel <= 31) return (int) ((2.5 * sqrt(targetLevel)) - (40.5 * targetLevel) + 360);
        return (int) ((4.5 * sqrt(targetLevel)) - (162.5 * targetLevel) + 2220);
    }

    public static int getXPRequiredForNextLevel(final int currentLevel) {
        if (currentLevel <= 15) return (2 * currentLevel) + 7;
        if (currentLevel <= 30) return (5 * currentLevel) - 38;
        return (9 * currentLevel) - 158;
    }

    public static void dropExp(final Location location, final int xp) {
        final ExperienceOrb orb = (ExperienceOrb) location.getWorld().spawnEntity(location, EntityType.EXPERIENCE_ORB);
        orb.setExperience(xp);
    }

    private static int sqrt(final int a) {
        return a * a;
    }
}
