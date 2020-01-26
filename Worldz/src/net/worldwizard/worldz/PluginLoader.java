package net.worldwizard.worldz;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

import net.worldwizard.worldz.world.Extension;

public class PluginLoader {
    public static Object loadPlugin(final String name) {
        final String basePath = PluginRegistration.getBasePath();
        try {
            final URL[] loadPath = { new URL("jar:file:" + basePath
                    + "/Plugins/" + name
                    + Extension.getPluginExtensionWithPeriod() + "!/") };
            try (final URLClassLoader instance = URLClassLoader
                    .newInstance(loadPath)) {
                return instance
                        .loadClass("net.worldwizard.worldz.plugins." + name)
                        .newInstance();
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
            PluginHooks.hookResourceOverrides(plugin);
        }
    }
}
