/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2;

import studio.ignitionigloogames.commondialogs.CommonDialogs;
import studio.ignitionigloogames.errorlogger.ErrorLogger;
import studio.ignitionigloogames.twistedtrek.import2.creatures.AbstractCreature;

public class Import2 {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "Import2";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
	    + "Include the error log with your bug report.\n" + "Email bug reports to: products@puttysoftware.com\n"
	    + "Subject: Import2 Bug Report";
    private static final String ERROR_TITLE = "Import2 Error";
    private static final ErrorLogger elog = new ErrorLogger(Import2.PROGRAM_NAME);
    private static final int BATTLE_MAZE_SIZE = 16;

    // Methods
    public static Application getApplication() {
	return Import2.application;
    }

    public static int getBattleMazeSize() {
	return Import2.BATTLE_MAZE_SIZE;
    }

    public static ErrorLogger getErrorLogger() {
	// Display error message
	CommonDialogs.showErrorDialog(Import2.ERROR_MESSAGE, Import2.ERROR_TITLE);
	return Import2.elog;
    }

    public static void preInit() {
	// Compute action cap
	AbstractCreature.computeActionCap(Import2.BATTLE_MAZE_SIZE, Import2.BATTLE_MAZE_SIZE);
    }

    public static void main_disabled(final String[] args) {
	try {
	    // Pre-Init
	    Import2.preInit();
	    // Integrate with host platform
	    // Platform.hookLAF(Import2.PROGRAM_NAME);
	    Import2.application = new Application();
	    Import2.application.postConstruct();
	    Application.playLogoSound();
	    Import2.application.getGUIManager().showGUI();
	    // Register platform hooks
	    // Platform.hookAbout(Import2.application.getAboutDialog(),
	    // Import2.application.getAboutDialog().getClass().getDeclaredMethod("showAboutDialog"));
	    // Platform.hookPreferences(PreferencesManager.class,
	    // PreferencesManager.class.getDeclaredMethod("showPrefs"));
	    // Platform.hookQuit(Import2.application.getGUIManager(),
	    // Import2.application.getGUIManager().getClass().getDeclaredMethod("quitHandler"));
	    // Platform.hookDockIcon(LogoManager.getLogo());
	    // Set up Common Dialogs
	    CommonDialogs.setDefaultTitle(Import2.PROGRAM_NAME);
	    CommonDialogs.setIcon(Application.getMicroLogo());
	} catch (final Throwable t) {
	    Import2.getErrorLogger().logError(t);
	}
    }
}
