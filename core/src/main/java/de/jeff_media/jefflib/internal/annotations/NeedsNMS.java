package de.jeff_media.jefflib.internal.annotations;

import org.bukkit.plugin.Plugin;

/**
 * Indicates that the annotated method uses NMS and will only work in a supported Minecraft version, and only after
 * JeffLib has been initialized
 * @see NeedsInit
 */
public @interface NeedsNMS {
}
