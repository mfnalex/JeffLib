package de.jeff_media.jefflib.internal.annotations;

import org.bukkit.plugin.Plugin;

/**
 * Indicates that the annotated method may only be called after calling {@link de.jeff_media.jefflib.JeffLib#init(Plugin)}
 */
public @interface NeedsInit {
}
