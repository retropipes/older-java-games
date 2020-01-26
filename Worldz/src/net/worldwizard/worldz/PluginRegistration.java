package net.worldwizard.worldz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import net.worldwizard.io.ResourceStreamReader;
import net.worldwizard.worldz.world.Extension;

public class PluginRegistration {
    public static void registerPlugin() {
        final Vector<String> registeredNames = PluginRegistration
                .readPluginRegistry();
        boolean anyFound = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                anyFound = true;
            }
        }
        // Load plugin list
        String[] pluginList = null;
        if (registeredNames != null && anyFound) {
            registeredNames.trimToSize();
            pluginList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                pluginList[x] = name;
            }
        }
        // Pick plugin to register
        final String res = Messager.showTextInputDialog(
                "Register Which Plugin?", "Register Plugin");
        if (res != null) {
            // Verify that plugin is not already registered
            boolean alreadyRegistered = false;
            if (pluginList != null) {
                for (final String element : pluginList) {
                    if (element.equalsIgnoreCase(res)) {
                        alreadyRegistered = true;
                        break;
                    }
                }
            }
            if (!alreadyRegistered) {
                // Verify that plugin file exists
                if (new File(PluginRegistration.getBasePath() + File.separator
                        + "Plugins" + File.separator + res
                        + Extension.getPluginExtensionWithPeriod()).exists()) {
                    // Register it
                    if (pluginList != null && anyFound) {
                        final String[] newPluginList = new String[pluginList.length
                                + 1];
                        for (int x = 0; x < newPluginList.length; x++) {
                            if (x < pluginList.length) {
                                newPluginList[x] = pluginList[x];
                            } else {
                                newPluginList[x] = res;
                            }
                        }
                        PluginRegistration.writePluginRegistry(newPluginList);
                    } else {
                        PluginRegistration
                                .writePluginRegistry(new String[] { res });
                    }
                    final Object plugin = PluginLoader.loadPlugin(res);
                    PluginLoader.injectNewlyRegisteredPlugin(plugin);
                    Messager.showDialog(
                            "Plugin successfully registered and loaded.");
                } else {
                    Messager.showDialog(
                            "The plugin to register is not a valid plugin.");
                }
            } else {
                Messager.showDialog(
                        "The plugin to register has been registered already.");
            }
        }
    }

    public static void unregisterPlugin() {
        final Vector<String> registeredNames = PluginRegistration
                .readPluginRegistry();
        boolean anyFound = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                anyFound = true;
            }
        }
        // Load plugin list
        String[] pluginList = null;
        if (registeredNames != null && anyFound) {
            registeredNames.trimToSize();
            pluginList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                pluginList[x] = name;
            }
        } else {
            Messager.showTitledDialog("No Plugins Registered!",
                    "Unregister Plugin");
            return;
        }
        // Pick plugin to unregister
        final String res = Messager.showInputDialog("Unregister Which Plugin?",
                "Unregister Plugin", pluginList, pluginList[0]);
        if (res != null) {
            // Find plugin index
            int index = -1;
            for (int x = 0; x < pluginList.length; x++) {
                if (pluginList[x].equals(res)) {
                    index = x;
                    break;
                }
            }
            if (index != -1) {
                // Unregister it
                if (pluginList.length > 1) {
                    pluginList[index] = null;
                    final String[] newPluginList = new String[pluginList.length
                            - 1];
                    int offset = 0;
                    for (int x = 0; x < newPluginList.length; x++) {
                        if (pluginList[x + offset] != null) {
                            newPluginList[x] = pluginList[x + offset];
                        } else {
                            offset++;
                        }
                    }
                    PluginRegistration.writePluginRegistry(newPluginList);
                } else {
                    PluginRegistration.writePluginRegistry(null);
                }
                if (anyFound) {
                    Messager.showDialog(
                            "Note that newly unregistered plugins will not be unloaded until the program is restarted.");
                }
            }
        }
    }

    static Vector<String> readPluginRegistry() {
        final String basePath = PluginRegistration.getBasePath();
        // Load plugin registry file
        final Vector<String> registeredNames = new Vector<>();
        try (final FileInputStream fis = new FileInputStream(basePath
                + File.separator + "Plugins" + File.separator + "PluginRegistry"
                + Extension.getRegistryExtensionWithPeriod())) {
            final ResourceStreamReader rsr = new ResourceStreamReader(fis);
            String input = "";
            while (input != null) {
                input = rsr.readString();
                if (input != null) {
                    registeredNames.add(input);
                }
            }
            rsr.close();
        } catch (final IOException io) {
            // Abort
            return null;
        }
        return registeredNames;
    }

    private static void writePluginRegistry(final String[] newPluginList) {
        final String basePath = PluginRegistration.getBasePath();
        // Save plugin registry file
        try (final BufferedWriter bw = new BufferedWriter(
                new FileWriter(basePath + File.separator + "Plugins"
                        + File.separator + "PluginRegistry"
                        + Extension.getRegistryExtensionWithPeriod()))) {
            if (newPluginList != null) {
                for (int x = 0; x < newPluginList.length; x++) {
                    if (x != newPluginList.length - 1) {
                        bw.write(newPluginList[x] + "\n");
                    } else {
                        bw.write(newPluginList[x]);
                    }
                }
            }
            bw.close();
        } catch (final IOException io) {
            // Abort
        }
    }

    public static String getBasePath() {
        // Detect if we're in a Mac OS X-style application package
        String basePath;
        if (new File("../../MacOS/JavaApplicationStub").canExecute()) {
            basePath = "../../../..";
        } else {
            basePath = ".";
        }
        return basePath;
    }
}
