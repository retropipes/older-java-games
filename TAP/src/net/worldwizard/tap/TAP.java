/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.tap;

import net.worldwizard.ghosted.Ghosted;
import net.worldwizard.tap.platform.Platform;

public class TAP {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "TAP";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Email bug reports to: tap@worldwizard.net\n"
            + "Subject: TAP Bug Report";
    private static final String ERROR_TITLE = "TAP Error";
    private static final Ghosted debug = new Ghosted(TAP.PROGRAM_NAME,
            TAP.ERROR_MESSAGE, TAP.ERROR_TITLE);

    // Methods
    public static Application getApplication() {
        return TAP.application;
    }

    public static Ghosted getDebug() {
        return TAP.debug;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            final Platform platform = new Platform();
            platform.hookLAF();
            TAP.application = new Application();
            TAP.application.postConstruct();
            TAP.application.getGUIManager().showGUI();
            // Register platform hooks
            platform.hookAbout(TAP.application.getAboutDialog(),
                    TAP.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            platform.hookFileOpen(
                    TAP.application.getAdventureManager(),
                    TAP.application
                            .getAdventureManager()
                            .getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class), s);
            platform.hookQuit(TAP.getApplication().getGUIManager(), TAP
                    .getApplication().getGUIManager().getClass()
                    .getDeclaredMethod("quitHandler"));
        } catch (final Throwable t) {
            TAP.getDebug().debug(t);
        }
    }
}
