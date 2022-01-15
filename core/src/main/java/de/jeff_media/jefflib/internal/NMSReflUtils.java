package de.jeff_media.jefflib.internal;

import com.allatori.annotations.DoNotRename;
import de.jeff_media.jefflib.ReflUtils;

@InternalOnly
@DoNotRename
public class NMSReflUtils {

    @DoNotRename
    public static void setField(final Class<?> clazz, final Object object, final String fieldName, final Object value) {
        ReflUtils.setField(clazz,object,fieldName,value);
    }
}