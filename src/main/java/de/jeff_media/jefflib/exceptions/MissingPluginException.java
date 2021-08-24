package de.jeff_media.jefflib.exceptions;

/**
 * Called when a plugin is not installed that is required by a specific API method
 */
public class MissingPluginException extends Exception {

    public MissingPluginException(String pluginName) {
        super("Plugin is not installed, but required for this operation: " + pluginName);
    }
}
