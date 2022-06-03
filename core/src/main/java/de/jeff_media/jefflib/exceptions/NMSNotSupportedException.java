package de.jeff_media.jefflib.exceptions;

import com.allatori.annotations.DoNotRename;
import de.jeff_media.jefflib.JeffLib;

@DoNotRename
public class NMSNotSupportedException extends RuntimeException {

    public static void check() throws NMSNotSupportedException {
        if(JeffLib.getPlugin() == null) {
            throw new JeffLibNotInitializedException();
        }
        if(JeffLib.getNMSHandler() == null) {
            throw new NMSNotSupportedException();
        }
    }

    public NMSNotSupportedException() {
        super("Could not find an NeedsNMS handler for the current Minecraft version.");
    }

}
