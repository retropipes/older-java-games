/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.platform.Platform;
import com.puttysoftware.widgetwarren.prefs.PreferencesManager;
import com.puttysoftware.widgetwarren.resourcemanagers.LogoManager;

public class WidgetWarren {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "WidgetWarren";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: WidgetWarren Bug Report";
    private static final String ERROR_TITLE = "WidgetWarren Error";
    private static final ErrorLogger errorLogger = new ErrorLogger(
            WidgetWarren.PROGRAM_NAME);

    // Methods
    public static Application getApplication() {
        return WidgetWarren.application;
    }

    public static ErrorLogger getErrorLogger() {
        CommonDialogs.showErrorDialog(WidgetWarren.ERROR_MESSAGE,
                WidgetWarren.ERROR_TITLE);
        return WidgetWarren.errorLogger;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            Platform.hookLAF(WidgetWarren.PROGRAM_NAME);
            WidgetWarren.application = new Application();
            WidgetWarren.application.postConstruct();
            WidgetWarren.application.playLogoSound();
            WidgetWarren.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(WidgetWarren.application.getAboutDialog(),
                    WidgetWarren.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(WidgetWarren.application.getGUIManager(),
                    WidgetWarren.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            Platform.hookDockIcon(LogoManager.getLogo());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(WidgetWarren.PROGRAM_NAME);
            CommonDialogs.setIcon(LogoManager.getMicroLogo());
        } catch (final Throwable t) {
            WidgetWarren.getErrorLogger().logError(t);
        }
    }
}
