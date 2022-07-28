package com.jeff_media.jefflib;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Reflection related methods
 */
@SuppressWarnings("NonThreadSafeLazyInitialization")
@UtilityClass
public final class ReflUtils {

    private static final Map<String, Class<?>> CLASSES = new HashMap<>();
    private static final Table<Class<?>, String, Method> METHODS_NO_ARGS = HashBasedTable.create();
    private static final Table<Class<?>, MethodParameters, Method> METHODS_WITH_ARGS = HashBasedTable.create();
    private static final Table<Class<?>, String, Field> FIELDS = HashBasedTable.create();
    private static final Map<Class<?>, Constructor<?>> CONSTRUCTORS_NO_ARGS = new HashMap<>();
    private static final Table<Class<?>, Parameters, Constructor<?>> CONSTRUCTOR_WITH_ARGS = HashBasedTable.create();
    private static String nmsVersion;

    /**
     * @deprecated Doesn't work on 1.17+
     */
    @Deprecated
    public static Class<?> getNMSClass(final String className) {
        return getClassCached("net.minecraft.server." + getNMSVersion() + "." + className);
    }

    /**
     * Gets a class
     *
     * @return The class, or null if not found
     */
    public static @Nullable Class<?> getClassCached(final @Nonnull String className) {
        if (CLASSES.containsKey(className)) {
            return CLASSES.get(className);
        }
        try {
            final Class<?> classForName = Class.forName(className);
            CLASSES.put(className, classForName);
            return classForName;
        } catch (final ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Gets the NMS version String as used in the package name, e.g. "v1_19_R1"
     */
    public static String getNMSVersion() {
        if (nmsVersion == null) {
            return nmsVersion = "v" + McVersion.current().getNmsVersion();
        }
        return nmsVersion;
    }

    /**
     * Gets a class from org.bukkit.craftbukkit
     */
    public static Class<?> getOBCClass(final String className) {
        return getClassCached("org.bukkit.craftbukkit." + getNMSVersion() + "." + className);
    }

    /**
     * Gets whether a class is already cached
     */
    public static boolean isClassCached(final String className) {
        return CLASSES.containsKey(className);
    }

    /**
     * Gets whether a method is already cached
     */
    public static boolean isMethodCached(final @Nonnull Class<?> clazz, final @Nonnull String methodName) {
        return METHODS_NO_ARGS.contains(clazz, methodName);
    }

    /**
     * Gets a method without parameters
     *
     * @return The method, or null if not found
     */
    public static @Nullable Method getMethodCached(final @Nonnull Class<?> clazz, final @Nonnull String methodName) {
        if (METHODS_NO_ARGS.contains(clazz, methodName)) {
            return METHODS_NO_ARGS.get(clazz, methodName);
        }
        try {
            final Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            METHODS_NO_ARGS.put(clazz, methodName, method);
            return method;
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Gets whether a method with parameters is already cached
     */
    public static boolean isMethodCached(final @Nonnull Class<?> clazz, final @Nonnull String methodName, final @Nonnull Class<?>... params) {
        return METHODS_WITH_ARGS.contains(clazz, new MethodParameters(methodName, params));
    }

    /**
     * Gets a method with parameters, or null if not found
     *
     * @return The method, or null if not found
     */
    public static Method getMethodCached(final @Nonnull Class<?> clazz, final @Nonnull String methodName, final @Nonnull Class<?>... params) {
        final MethodParameters methodParameters = new MethodParameters(methodName, params);
        if (METHODS_WITH_ARGS.contains(clazz, methodParameters)) {
            return METHODS_WITH_ARGS.get(clazz, methodParameters);
        }
        try {
            final Method method = clazz.getDeclaredMethod(methodName, params);
            method.setAccessible(true);
            METHODS_WITH_ARGS.put(clazz, methodParameters, method);
            return method;
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Sets an object's field to the given value
     */
    public static void setField(final @Nonnull Object object, final @Nonnull String fieldName, final @Nullable Object value) {
        setField(object.getClass(), object, fieldName, value);
    }

    /**
     * Sets an object's field to the given value.
     *
     * @param clazz     Class where this field is declared
     * @param object    Object to set the field on, or null for static fields
     * @param fieldName Name of the field to set
     * @param value     Value to set the field to
     */
    public static void setField(final @Nonnull Class<?> clazz, final @Nullable Object object, final @Nonnull String fieldName, final @Nullable Object value) {
        try {
            final Field field = getFieldCached(clazz, fieldName);
            java.util.Objects.requireNonNull(field).set(object, value);
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets an object's field, or null if not found
     *
     * @return The field, or null if not found
     */
    public static Field getFieldCached(final @Nonnull Class<?> clazz, final @Nonnull String fieldName) {
        if (FIELDS.contains(clazz, fieldName)) {
            return FIELDS.get(clazz, fieldName);
        }
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            FIELDS.put(clazz, fieldName, field);
            return field;
        } catch (final NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Gets whether a field is already cached
     */
    public static boolean isFieldCached(final @Nonnull Class<?> clazz, final @Nonnull String fieldName) {
        return FIELDS.contains(clazz, fieldName);
    }

    /**
     * Gets if the no-args constructor is already cached
     */
    public static boolean isConstructorCached(final @Nonnull Class<?> clazz) {
        return CONSTRUCTORS_NO_ARGS.containsKey(clazz);
    }

    /**
     * Gets if the constructor with parameters is already cached
     */
    public static boolean isConstructorCached(final @Nonnull Class<?> clazz, final @Nonnull Class<?>... params) {
        return CONSTRUCTOR_WITH_ARGS.contains(clazz, new Parameters(params));
    }

    /**
     * Gets a no-args constructor of a class, or null if not found
     *
     * @return The constructor, or null if not found
     */
    public static Constructor<?> getConstructorCached(final @Nonnull Class<?> clazz) {
        if (CONSTRUCTORS_NO_ARGS.containsKey(clazz)) {
            return CONSTRUCTORS_NO_ARGS.get(clazz);
        }
        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            CONSTRUCTORS_NO_ARGS.put(clazz, constructor);
            return constructor;
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Gets a constructor with parameters, or null if not found
     *
     * @return The constructor, or null if not found
     */
    public static Constructor<?> getConstructorCached(final @Nonnull Class<?> clazz, final @Nullable Class<?>... params) {
        final Parameters constructorParams = new Parameters(params);
        if (CONSTRUCTOR_WITH_ARGS.contains(clazz, constructorParams)) {
            return CONSTRUCTOR_WITH_ARGS.get(clazz, constructorParams);
        }
        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor(params);
            constructor.setAccessible(true);
            CONSTRUCTOR_WITH_ARGS.put(clazz, constructorParams, constructor);
            return constructor;
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }

    private static class Parameters {
        @Nonnull
        private final Class<?>[] parameterClazzes;

        private Parameters(@Nonnull Class<?>... parameterClazzes) {
            this.parameterClazzes = parameterClazzes;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(parameterClazzes);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Parameters that = (Parameters) o;
            return Arrays.equals(parameterClazzes, that.parameterClazzes);
        }
    }

    private static final class MethodParameters extends Parameters {

        @Nonnull
        private final String name;

        MethodParameters(final @Nonnull String name, final @Nonnull Class<?>... params) {
            super(params);
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            MethodParameters that = (MethodParameters) o;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), name);
        }


    }
}
