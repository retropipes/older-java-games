package com.puttysoftware.weaselweb.pluginmanagers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.weaselweb.maze.FileExtension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PluginRegistration {
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/WeaselWeb/plugins/";
    private static final String WIN_DIR = "\\Putty Software\\WeaselWeb\\plugins\\";
    private static final String UNIX_DIR = "/.puttysoftware/weaselweb/plugins/";

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
        // Get list of files in plugins folder
        final String[] pluginNames = new File(PluginRegistration.getBasePath()
                + File.separator + "plugins").list(new PluginFilter());
        if (pluginNames != null && pluginNames.length > 0) {
            // Strip extension from list entries
            for (int z = 0; z < pluginNames.length; z++) {
                pluginNames[z] = pluginNames[z].substring(0,
                        pluginNames[z].length() - 4);
            }
            // Pick plugin to register
            final String res = CommonDialogs.showInputDialog(
                    "Register Which Plugin?", "Register Plugin", pluginNames,
                    pluginNames[0]);
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
                    if (new File(PluginRegistration.getBasePath()
                            + File.separator + "plugins" + File.separator + res
                            + FileExtension.getPluginExtensionWithPeriod())
                            .exists()) {
                        // Register it
                        if (pluginList != null && anyFound) {
                            final String[] newpluginList = new String[pluginList.length + 1];
                            for (int x = 0; x < newpluginList.length; x++) {
                                if (x < pluginList.length) {
                                    newpluginList[x] = pluginList[x];
                                } else {
                                    newpluginList[x] = res;
                                }
                            }
                            PluginRegistration
                                    .writePluginRegistry(newpluginList);
                        } else {
                            PluginRegistration
                                    .writePluginRegistry(new String[] { res });
                        }
                        final Object plugin = PluginLoader.loadPlugin(res);
                        PluginLoader.injectNewlyRegisteredPlugin(plugin);
                        CommonDialogs
                                .showDialog("Plugin successfully registered and loaded.");
                    } else {
                        CommonDialogs
                                .showDialog("The plugin to register is not a valid plugin.");
                    }
                } else {
                    CommonDialogs
                            .showDialog("The plugin to register has been registered already.");
                }
            }
        } else {
            CommonDialogs.showDialog("No plugins found to register!");
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
            CommonDialogs.showTitledDialog("No Plugins Registered!",
                    "Unregister Plugin");
            return;
        }
        // Pick plugin to unregister
        final String res = CommonDialogs.showInputDialog(
                "Unregister Which Plugin?", "Unregister Plugin", pluginList,
                pluginList[0]);
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
                    final String[] newpluginList = new String[pluginList.length - 1];
                    int offset = 0;
                    for (int x = 0; x < pluginList.length; x++) {
                        if (pluginList[x] != null) {
                            newpluginList[x - offset] = pluginList[x];
                        } else {
                            offset++;
                        }
                    }
                    PluginRegistration.writePluginRegistry(newpluginList);
                } else {
                    PluginRegistration.writePluginRegistry(null);
                }
                final Object p = PluginLoader.getPluginByName(res);
                if (PluginHooks.areImagesHookedBy(p)) {
                    // Unhook
                    PluginHooks.unhookImageOverride();
                }
                if (PluginHooks.areStatImagesHookedBy(p)) {
                    // Unhook
                    PluginHooks.unhookStatImageOverride();
                }
                if (PluginHooks.areSoundsHookedBy(p)) {
                    // Unhook
                    PluginHooks.unhookSoundOverride();
                }
                if (PluginHooks.isMusicHookedBy(p)) {
                    // Unhook
                    PluginHooks.unhookMusicOverride();
                }
                if (PluginHooks.areMenusHookedBy(p)) {
                    // Unhook
                    PluginHooks.unhookMenuOverrides(p);
                }
                PluginLoader.unloadPlugin(p);
                CommonDialogs
                        .showDialog("Plugin successfully unregistered and unloaded.");
            }
        }
    }

    static Vector<String> readPluginRegistry() {
        final String basePath = PluginRegistration.getBasePath();
        // Load plugin registry file
        final Vector<String> registeredNames = new Vector<>();
        try (final FileInputStream fis = new FileInputStream(basePath
                + File.separator + "PluginRegistry"
                + FileExtension.getRegistryExtensionWithPeriod())) {
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

    private static void writePluginRegistry(final String[] newpluginList) {
        final String basePath = PluginRegistration.getBasePath();
        // Make folders, if needed
        final File pluginPerUserFolder = new File(basePath);
        if (!pluginPerUserFolder.exists()) {
            final boolean res = pluginPerUserFolder.mkdirs();
            if (!res) {
                return;
            }
        }
        // Save plugin registry file
        try (final BufferedWriter bw = new BufferedWriter(new FileWriter(
                basePath + File.separator + "PluginRegistry"
                        + FileExtension.getRegistryExtensionWithPeriod()))) {
            if (newpluginList != null) {
                for (int x = 0; x < newpluginList.length; x++) {
                    if (newpluginList[x] != null) {
                        if (x != newpluginList.length - 1) {
                            bw.write(newpluginList[x] + "\n");
                        } else {
                            bw.write(newpluginList[x]);
                        }
                    }
                }
            }
            bw.close();
        } catch (final IOException io) {
            // Abort
        }
    }

    public static String getBasePath() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(PluginRegistration.MAC_PREFIX)
                    + PluginRegistration.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(PluginRegistration.WIN_PREFIX)
                    + PluginRegistration.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return System.getenv(PluginRegistration.UNIX_PREFIX)
                    + PluginRegistration.UNIX_DIR;
        }
    }
}
