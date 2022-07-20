package com.jeff_media.jefflib.exceptions;

import com.jeff_media.jefflib.LocationUtils;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Gets thrown when a location definition inside a ConfigurationSection is invalid. See {@link LocationUtils#getLocationFromSection(ConfigurationSection, World)}
 */
public final class InvalidLocationDefinitionException extends IllegalArgumentException {

    public InvalidLocationDefinitionException(final String errorMessage) {
        super(errorMessage);
    }

}

