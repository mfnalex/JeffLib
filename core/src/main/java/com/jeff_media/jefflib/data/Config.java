package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.JeffLib;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Objects;

/**
 * Represents a configuration that automatically loads the default values from the plugin's resources.
 */
public class Config extends YamlConfiguration {

    private final String filename;
    private final File file;

    public Config(@Nonnull final  String filename) {
        this.filename = filename;
        file = new File(JeffLib.getPlugin().getDataFolder(), filename);
        loadDefaults();
        reload();
    }

    /**
     * Reloads the configuration
     */
    public void reload() {
        saveDefaultConfig();
        try {
            load(file);
        } catch (final IOException exception) {
            new IllegalArgumentException("Could not find or load file " + filename, exception).printStackTrace();
        } catch (final InvalidConfigurationException exception) {
            JeffLib.getLogger().severe("Your config file " + filename + " is invalid, using default values now. Please fix the below mentioned errors and try again:");
            exception.printStackTrace();
        }
    }

    private void saveDefaultConfig() {
        if (!file.exists()) {
            File parent = file.getParentFile();
            if(parent != null) {
                parent.mkdirs();
            }
            JeffLib.getPlugin().saveResource(filename, false);
        }
    }

    private void loadDefaults() {
        final YamlConfiguration defaultConfig = new YamlConfiguration();

        try (final InputStream inputStream = JeffLib.getPlugin().getResource(filename); final Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
            defaultConfig.load(reader);
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Could not load included config file " + filename, exception);
        } catch (final InvalidConfigurationException exception) {
            throw new IllegalArgumentException("Invalid default config for " + filename, exception);
        }

        setDefaults(defaultConfig);
    }

}
