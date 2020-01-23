package net.worldwizard.worldz;

import java.lang.reflect.Method;
import java.util.Vector;

import javax.swing.JMenuItem;

import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.resourcemanagers.SoundManager;

public class PluginHooks {
    public static void injectLateHooks(final Vector<Object> plugins) {
        for (final Object plugin : plugins) {
            PluginHooks.addMenus(plugin);
        }
    }

    public static void injectEarlyHooks(final Vector<Object> plugins) {
        for (final Object plugin : plugins) {
            PluginHooks.hookResourceOverrides(plugin);
        }
    }

    public static void addMenus(final Object plugin) {
        if (plugin != null) {
            try {
                String menuName;
                boolean leaveGame;
                final Method menuItemsMethod = plugin.getClass()
                        .getDeclaredMethod("getMenuItems");
                final JMenuItem[] newMenuItems = (JMenuItem[]) menuItemsMethod
                        .invoke(plugin);
                try {
                    final Method menuNameMethod = plugin.getClass()
                            .getDeclaredMethod("getDestinationMenu");
                    menuName = (String) menuNameMethod.invoke(plugin);
                } catch (final Exception e) {
                    menuName = "Plugin";
                }
                try {
                    final Method menuLeavesGameMethod = plugin.getClass()
                            .getDeclaredMethod("doMenuItemsLeaveGame");
                    leaveGame = ((Boolean) menuLeavesGameMethod.invoke(plugin))
                            .booleanValue();
                } catch (final Exception e) {
                    leaveGame = true;
                }
                final MenuManager mm = Worldz.getApplication().getMenuManager();
                mm.dynAddMenuItems(newMenuItems, menuName, leaveGame);
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    public static void hookResourceOverrides(final Object plugin) {
        if (plugin != null) {
            String imagePath;
            String soundPath;
            try {
                final Method imagePathMethod = plugin.getClass()
                        .getDeclaredMethod("getImagePath");
                imagePath = (String) imagePathMethod.invoke(plugin);
                GraphicsManager.useCustomLoadPath(imagePath, plugin);
            } catch (final Exception e) {
                // Ignore
            }
            try {
                final Method soundPathMethod = plugin.getClass()
                        .getDeclaredMethod("getSoundPath");
                soundPath = (String) soundPathMethod.invoke(plugin);
                SoundManager.useCustomLoadPath(soundPath, plugin);
            } catch (final Exception e) {
                // Ignore
            }
        }
    }
}
