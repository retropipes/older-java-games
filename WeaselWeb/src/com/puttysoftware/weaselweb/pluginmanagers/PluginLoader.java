package com.puttysoftware.weaselweb.pluginmanagers;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

import com.puttysoftware.weaselweb.maze.FileExtension;

public class PluginLoader {
    private static Vector<Object> LOADED_PLUGINS = new Vector<>();

    public static Object getPluginByName(final String name) {
        for (final Object o : PluginLoader.LOADED_PLUGINS) {
            if (o.getClass().getName()
                    .equals("com.puttysoftware.weaselweb.plugins." + name)) {
                return o;
            }
        }
        return null;
    }

    public static void unloadPlugin(final Object plugin) {
        PluginLoader.LOADED_PLUGINS.remove(plugin);
    }

    public static Object loadPlugin(final String name) {
        final String basePath = PluginRegistration.getBasePath();
        try {
            final URL[] loadPath = { new URL("jar:file:" + basePath
                    + "/plugins/" + name
                    + FileExtension.getPluginExtensionWithPeriod() + "!/") };
            try (final URLClassLoader instance = URLClassLoader
                    .newInstance(loadPath)) {
                final Object plugin = instance
                        .loadClass(
                                "com.puttysoftware.weaselweb.plugins." + name)
                        .newInstance();
                PluginLoader.LOADED_PLUGINS.add(plugin);
                return plugin;
            }
        } catch (final Exception e) {
            // Ignore
        }
        return null;
    }

    public static Vector<Object> loadAllRegisteredPlugins() {
        final Vector<String> registeredNames = PluginRegistration
                .readPluginRegistry();
        final Vector<Object> pluginObjects = new Vector<>();
        if (registeredNames != null) {
            // Load plugins
            registeredNames.trimToSize();
            for (final String name : registeredNames) {
                final Object pluginWithName = PluginLoader.loadPlugin(name);
                if (pluginWithName != null) {
                    // Add it to the vector
                    pluginObjects.add(pluginWithName);
                }
            }
        }
        return pluginObjects;
    }

    static void injectNewlyRegisteredPlugin(final Object plugin) {
        if (plugin != null) {
            // Inject menus
            PluginHooks.addMenus(plugin);
            // Inject resources
            PluginHooks.hookImageOverride(plugin);
            PluginHooks.hookStatImageOverride(plugin);
            PluginHooks.hookSoundOverride(plugin);
            PluginHooks.hookMusicOverride(plugin);
        }
    }
}
