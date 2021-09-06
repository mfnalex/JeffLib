package de.jeff_media.jefflib.exceptions;

import de.jeff_media.jefflib.JeffLib;
import org.bukkit.plugin.Plugin;

/**
 * Gets thrown when a plugin attempts to use an API method that needs initialization first, without having called {@link JeffLib#init(Plugin)} or {@link JeffLib#init(Plugin, boolean)} first.
 */
public final class JeffLibNotInitializedException extends IllegalStateException {

    public JeffLibNotInitializedException() {
        super("JeffLib hasn't been initialized. Use JeffLib#init before using this method.");
    }

}
