package com.jeff_media.jefflib.exceptions;

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.JeffLib;

@DoNotRename
public class NMSNotSupportedException extends RuntimeException {

    public NMSNotSupportedException(final String message) {
        super(message);
    }

    public NMSNotSupportedException() {
        super();
    }

    @Deprecated
    public static void check() throws NMSNotSupportedException {
        if (JeffLib.getNMSHandler() == null) {
            throw new NMSNotSupportedException();
        }
    }

}
