package de.jeff_media.jefflib.exceptions;

import com.allatori.annotations.DoNotRename;
import de.jeff_media.jefflib.JeffLib;

@DoNotRename
public class NMSNotSupportedException extends RuntimeException {

    @Deprecated
    public static void check() throws NMSNotSupportedException {
        if(JeffLib.getNMSHandler() == null) {
            throw new NMSNotSupportedException();
        }
    }

    public NMSNotSupportedException(final String message) {
        super(message);
    }

    public NMSNotSupportedException() {
        super();
    }

}
