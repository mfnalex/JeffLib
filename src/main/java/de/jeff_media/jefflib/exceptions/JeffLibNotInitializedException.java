package de.jeff_media.jefflib.exceptions;

import org.bukkit.plugin.Plugin;

/**
 * Gets thrown when a plugin attempts to use an API method that needs initialization first, without having called {@link de.jeff_media.jefflib.JeffLib#init(Plugin)} or {@link de.jeff_media.jefflib.JeffLib#init(Plugin, boolean)} first.
 */
public final class JeffLibNotInitializedException extends RuntimeException {

    public JeffLibNotInitializedException() {
        super("JeffLib hasn't been initialized. Use JeffLib#init before using this method.");
    }

}
