/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class related methods that do not have something to do with Reflection (see {@link ReflUtils} for that)
 */
@UtilityClass
public class ClassUtils {

    /**
     * Checks if a class exists
     *
     * @param name Fully qualified class name
     * @return true if the class exists, otherwise false
     */
    public static boolean exists(@Nonnull final String name) {
        if(ReflUtils.isClassCached(name)) {
            return true;
        }
        try {
            Class.forName(name);
            return true;
        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }

    public static int getCurrentLineNumber() {
        return getCurrentLineNumber(1);
    }

    /**
     * Returns the current line number from which this method was called from
     */
    public static int getCurrentLineNumber(final int offset) {
        return Thread.currentThread().getStackTrace()[2 + offset].getLineNumber();
    }

    public static String getCurrentClassName() {
        return getCurrentClassName(1);
    }

    /**
     * Returns the current class name from which this method was called from
     */
    public static String getCurrentClassName(int offset) {
        return Thread.currentThread().getStackTrace()[2 + offset].getClassName();
    }

    public static Class<?> getCurrentClass() {
        return getCurrentClass(1);
    }

    public static Class<?> getCurrentClass(final int offset) {
        try {
            return Class.forName(Thread.currentThread().getStackTrace()[2 + offset].getClassName());
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the current class file name from which this method was called from
     */
    public static String getCurrentClassFileName() {
        return getCurrentClassFileName(1);
    }

    public static String getCurrentClassFileName(int offset) {
        return Thread.currentThread().getStackTrace()[2 + offset].getFileName();
    }

    public static String getCurrentMethodName() {
        return getCurrentMethodName(1);
    }

    /**
     * Returns the current method name from which this method was called from
     */
    public static String getCurrentMethodName(int offset) {
        return Thread.currentThread().getStackTrace()[2 + offset].getMethodName();
    }

    /**
     * Returns a list of all classes included in the plugin's jar file. The returned classes have a format like "com.jeff_media.jefflib.ClassUtils"
     */
    @Nonnull
    public static List<String> listAllClasses() {
        return listAllClasses(ClassUtils.class);
    }

    /**
     * Returns a list of all classes included in the plugin's jar file that provides the given class. The returned classes have a format like "com.jeff_media.jefflib.ClassUtils"
     */
    @Nonnull
    public static List<String> listAllClasses(@Nonnull final Class<?> clazz) {
        final CodeSource source = clazz.getProtectionDomain().getCodeSource();
        if (source == null) return Collections.emptyList();
        final URL url = source.getLocation();
        try (
                final ZipInputStream zip = new ZipInputStream(url.openStream())) {
            final List<String> classes = new ArrayList<>();
            while (true) {
                final ZipEntry entry = zip.getNextEntry();
                if (entry == null) break;
                if (entry.isDirectory()) continue;
                final String name = entry.getName();
                if (name.endsWith(".class")) {
                    classes.add(name.replace('/', '.').substring(0, name.length() - 6));
                }
            }
            return classes;
        } catch (IOException exception) {
            return Collections.emptyList();
        }
    }
}
