/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.creatures.AbstractCreature;
import net.dynamicdungeon.dynamicdungeon.prefs.PreferencesManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.LogoManager;
import net.dynamicdungeon.errorlogger.ErrorLogger;
import net.dynamicdungeon.platform.Platform;

public class DynamicDungeon {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "DynamicDungeon";
    private static final String ERROR_MESSAGE = "Uh-oh! Something has gone wrong!";
    private static final String ERROR_TITLE = "DynamicDungeon Error";
    private static final ErrorLogger elog = new ErrorLogger(
            DynamicDungeon.PROGRAM_NAME);
    private static final int BATTLE_MAZE_SIZE = 16;

    // Methods
    public static Application getApplication() {
        return DynamicDungeon.application;
    }

    public static ErrorLogger getErrorLogger() {
        // Display error message
        CommonDialogs.showErrorDialog(DynamicDungeon.ERROR_MESSAGE,
                DynamicDungeon.ERROR_TITLE);
        return DynamicDungeon.elog;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(DynamicDungeon.BATTLE_MAZE_SIZE,
                DynamicDungeon.BATTLE_MAZE_SIZE);
        // HiDPI detection
        boolean isRetina = false;
        try {
            final GraphicsDevice graphicsDevice = GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getDefaultScreenDevice();
            final Field field = graphicsDevice.getClass()
                    .getDeclaredField("scale");
            if (field != null) {
                field.setAccessible(true);
                final Object scale = field.get(graphicsDevice);
                if (scale instanceof Integer
                        && ((Integer) scale).intValue() == 2) {
                    isRetina = true;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Ignore
        }
        PreferencesManager.setHighDefEnabled(isRetina);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            DynamicDungeon.preInit();
            // Integrate with host platform
            Platform.hookLAF(DynamicDungeon.PROGRAM_NAME);
            DynamicDungeon.application = new Application();
            DynamicDungeon.application.postConstruct();
            Application.playLogoSound();
            DynamicDungeon.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(DynamicDungeon.application.getAboutDialog(),
                    DynamicDungeon.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(DynamicDungeon.application.getGUIManager(),
                    DynamicDungeon.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            Platform.hookDockIcon(LogoManager.getLogo());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(DynamicDungeon.PROGRAM_NAME);
            CommonDialogs.setIcon(Application.getMicroLogo());
        } catch (final Throwable t) {
            DynamicDungeon.getErrorLogger().logError(t);
        }
    }
}
