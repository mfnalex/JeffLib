package de.jeff_media.jefflib.exceptions;

import com.allatori.annotations.DoNotRename;
import de.jeff_media.jefflib.JeffLib;
import org.bukkit.plugin.Plugin;

/**
 * Gets thrown when a plugin attempts to use an API method that needs initialization first, without having called {@link JeffLib#init(Plugin)} first.
 */
@DoNotRename
public final class JeffLibNotInitializedException extends IllegalStateException {

    public static void check() throws JeffLibNotInitializedException {
        if(JeffLib.getPlugin() == null) {
            throw new JeffLibNotInitializedException();
        }
    }

    public JeffLibNotInitializedException() {
        super("JeffLib hasn't been initialized. Use JeffLib#init before using this method.");
    }

}
