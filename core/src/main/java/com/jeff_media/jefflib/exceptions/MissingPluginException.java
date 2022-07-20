package com.jeff_media.jefflib.exceptions;

/**
 * Gets thrown when a plugin is not installed that is required by a specific API method
 */
public final class MissingPluginException extends Exception {

    public MissingPluginException(final String pluginName) {
        super("Plugin is not installed, but required for this operation: " + pluginName);
    }
}
