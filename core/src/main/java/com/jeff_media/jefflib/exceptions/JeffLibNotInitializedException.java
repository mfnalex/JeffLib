package com.jeff_media.jefflib.exceptions;

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.JeffLib;
import org.bukkit.plugin.Plugin;

/**
 * Gets thrown when a plugin attempts to use an API method that needs initialization first, without having called {@link JeffLib#init(Plugin)} first.
 *
 * @deprecated JeffLib 9.0.0+ automatically gets the plugin's instance
 */
@DoNotRename
@Deprecated
public final class JeffLibNotInitializedException extends IllegalStateException {

    public JeffLibNotInitializedException() {
        super("JeffLib hasn't been initialized. Use JeffLib#init before using this method.");
    }

    public static void check() throws JeffLibNotInitializedException {
        if (JeffLib.getPlugin() == null) {
            throw new JeffLibNotInitializedException();
        }
    }

}
