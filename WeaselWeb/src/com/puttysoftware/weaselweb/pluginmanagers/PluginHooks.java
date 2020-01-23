package com.puttysoftware.weaselweb.pluginmanagers;

import java.lang.reflect.Method;
import java.util.Vector;

import javax.swing.JMenuItem;

import com.puttysoftware.weaselweb.MenuManager;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.resourcemanagers.ImageManager;
import com.puttysoftware.weaselweb.resourcemanagers.MusicManager;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;
import com.puttysoftware.weaselweb.resourcemanagers.StatImageManager;

public class PluginHooks {
    private static Object IMAGE_HOOKER = null;
    private static Object STAT_IMAGE_HOOKER = null;
    private static Object SOUND_HOOKER = null;
    private static Object MUSIC_HOOKER = null;
    private static Vector<Object> MENU_HOOKERS = new Vector<>();
    private static Vector<JMenuItem[]> DYNAMIC_MENU_ITEMS = new Vector<>();
    private static Vector<String> DYNAMIC_MENU_NAMES = new Vector<>();

    public static void injectLateHooks(final Vector<Object> plugins) {
        for (final Object plugin : plugins) {
            PluginHooks.addMenus(plugin);
        }
    }

    public static void injectEarlyHooks(final Vector<Object> plugins) {
        for (final Object plugin : plugins) {
            PluginHooks.hookImageOverride(plugin);
            PluginHooks.hookSoundOverride(plugin);
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
                PluginHooks.DYNAMIC_MENU_ITEMS.add(newMenuItems);
                PluginHooks.DYNAMIC_MENU_NAMES.add(menuName);
                PluginHooks.MENU_HOOKERS.add(plugin);
                final MenuManager mm = WeaselWeb.getApplication()
                        .getMenuManager();
                mm.dynAddMenuItems(newMenuItems, menuName, leaveGame);
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    public static void hookImageOverride(final Object plugin) {
        if (plugin != null) {
            String imagePath;
            try {
                final Method imagePathMethod = plugin.getClass()
                        .getDeclaredMethod("getImagePath");
                imagePath = (String) imagePathMethod.invoke(plugin);
                ImageManager.useCustomLoadPath(imagePath, plugin);
                PluginHooks.IMAGE_HOOKER = plugin;
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    public static void hookStatImageOverride(final Object plugin) {
        if (plugin != null) {
            String imagePath;
            try {
                final Method imagePathMethod = plugin.getClass()
                        .getDeclaredMethod("getStatImagePath");
                imagePath = (String) imagePathMethod.invoke(plugin);
                StatImageManager.useCustomLoadPath(imagePath, plugin);
                PluginHooks.STAT_IMAGE_HOOKER = plugin;
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    public static void hookSoundOverride(final Object plugin) {
        if (plugin != null) {
            String soundPath;
            try {
                final Method soundPathMethod = plugin.getClass()
                        .getDeclaredMethod("getSoundPath");
                soundPath = (String) soundPathMethod.invoke(plugin);
                SoundManager.useCustomLoadPath(soundPath, plugin);
                PluginHooks.SOUND_HOOKER = plugin;
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    public static void hookMusicOverride(final Object plugin) {
        if (plugin != null) {
            String musicPath;
            try {
                final Method musicPathMethod = plugin.getClass()
                        .getDeclaredMethod("getMusicPath");
                musicPath = (String) musicPathMethod.invoke(plugin);
                MusicManager.useCustomLoadPath(musicPath, plugin);
                PluginHooks.MUSIC_HOOKER = plugin;
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

    public static boolean areMenusHookedBy(final Object plugin) {
        if (plugin != null) {
            for (final Object p : PluginHooks.MENU_HOOKERS) {
                if (p.getClass().equals(plugin.getClass())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public static boolean areImagesHookedBy(final Object plugin) {
        if (PluginHooks.IMAGE_HOOKER != null && plugin != null) {
            return plugin.getClass()
                    .equals(PluginHooks.IMAGE_HOOKER.getClass());
        } else {
            return false;
        }
    }

    public static boolean areStatImagesHookedBy(final Object plugin) {
        if (PluginHooks.STAT_IMAGE_HOOKER != null && plugin != null) {
            return plugin.getClass().equals(
                    PluginHooks.STAT_IMAGE_HOOKER.getClass());
        } else {
            return false;
        }
    }

    public static boolean areSoundsHookedBy(final Object plugin) {
        if (PluginHooks.SOUND_HOOKER != null && plugin != null) {
            return plugin.getClass()
                    .equals(PluginHooks.SOUND_HOOKER.getClass());
        } else {
            return false;
        }
    }

    public static boolean isMusicHookedBy(final Object plugin) {
        if (PluginHooks.MUSIC_HOOKER != null && plugin != null) {
            return plugin.getClass()
                    .equals(PluginHooks.MUSIC_HOOKER.getClass());
        } else {
            return false;
        }
    }

    public static void unhookMenuOverrides(final Object plugin) {
        if (plugin != null) {
            for (int z = 0; z < PluginHooks.MENU_HOOKERS.capacity(); z++) {
                final Object p = PluginHooks.MENU_HOOKERS.get(z);
                if (p != null) {
                    if (p.getClass().equals(plugin.getClass())) {
                        final JMenuItem[] items = PluginHooks.DYNAMIC_MENU_ITEMS
                                .get(z);
                        final String name = PluginHooks.DYNAMIC_MENU_NAMES
                                .get(z);
                        final MenuManager mm = WeaselWeb.getApplication()
                                .getMenuManager();
                        mm.dynRemoveMenuItems(items, name);
                        PluginHooks.MENU_HOOKERS.remove(z);
                        PluginHooks.DYNAMIC_MENU_ITEMS.remove(z);
                        PluginHooks.DYNAMIC_MENU_NAMES.remove(z);
                        return;
                    }
                }
            }
        }
    }

    public static void unhookImageOverride() {
        ImageManager.useDefaultLoadPath();
        PluginHooks.IMAGE_HOOKER = null;
    }

    public static void unhookStatImageOverride() {
        StatImageManager.useDefaultLoadPath();
        PluginHooks.STAT_IMAGE_HOOKER = null;
    }

    public static void unhookSoundOverride() {
        SoundManager.useDefaultLoadPath();
        PluginHooks.SOUND_HOOKER = null;
    }

    public static void unhookMusicOverride() {
        MusicManager.useDefaultLoadPath();
        PluginHooks.MUSIC_HOOKER = null;
    }
}
