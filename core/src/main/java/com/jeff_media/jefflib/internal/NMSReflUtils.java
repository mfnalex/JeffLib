package com.jeff_media.jefflib.internal;

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.internal.annotations.Internal;
import com.jeff_media.jefflib.ReflUtils;

@Internal
@DoNotRename
public final class NMSReflUtils {

    @DoNotRename
    public static void setField(final Class<?> clazz, final Object object, final String fieldName, final Object value) {
        ReflUtils.setField(clazz,object,fieldName,value);
    }
}