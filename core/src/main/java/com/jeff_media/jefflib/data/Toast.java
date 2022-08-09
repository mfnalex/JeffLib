package com.jeff_media.jefflib.data;

import com.google.gson.JsonObject;
import net.minecraft.advancements.FrameType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.UUID;

public class Toast {

    private final String title;
    private final String description;
    private final FrameType frameType;
    private final Material icon;
    private final NamespacedKey key = NamespacedKey.fromString("jeff_media_jefflib_toast:" + UUID.randomUUID().toString());

    public void send(Player player) {
        Advancement adv = registerMyself(title, description, icon, frameType);
        player.getAdvancementProgress(adv).awardCriteria("display");
        removeMyself();
    }

    public Toast(String title, String description, Material icon, FrameType frameType) {
        this.title = title;
        this.description = description;
        this.frameType = frameType;
        this.icon = icon;
    }

    private Advancement registerMyself(String title, String description, Material icon, FrameType frameType) {
        return Bukkit.getUnsafe().loadAdvancement(new NamespacedKey("jefflib", UUID.randomUUID().toString()), "{\"display\":{\"icon\": {\"item\": \"$MATERIAL$\"},\"frame\": \"$FRAMETYPE$\", \"title\": {\"translate\":\"$TITLE$\"},\"description\": {\"translate\":\"$DESCRIPTION$\"},\"announce_to_chat\": \"false\"},\"criteria\": {\"display\": {\"trigger\": \"minecraft:impossible\"}}}"
                .replace("$MATERIAL$", icon.getKey().toString())
                .replace("$TITLE$", title)
                .replace("$DESCRIPTION$", description)
                .replace("$FRAMETYPE$", frameType.getName()));

    }

    private Advancement registerMyself(String json) {
        return Bukkit.getUnsafe().loadAdvancement(key, json);
    }

    public void removeMyself() {
        if(!Bukkit.getUnsafe().removeAdvancement(key)) {
            Bukkit.getLogger().warning("Could not remove advancement " + key);
        }
    }

    public enum FrameType {
        CHALLENGE("challenge"),
        GOAL("goal"),
        TASK("task");

        private final String name;

        FrameType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
