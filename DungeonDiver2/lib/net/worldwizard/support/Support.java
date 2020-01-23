/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.errorlogger.ErrorLogger;
import net.worldwizard.support.variables.Variables;

public class Support {
    // Constants
    private static final String PROGRAM_NAME = "DungeonDiverII";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: DungeonDiverII Bug Report";
    private static final String SCRIPT_ERROR_MESSAGE = "A problem has occurred while running a script.\n"
            + "This error is non-fatal, and has been logged.";
    private static final String ERROR_TITLE = "DungeonDiverII Error";
    private static final ErrorLogger elog = new ErrorLogger(
            Support.PROGRAM_NAME);
    private static final int VERSION_MAJOR = 2;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_BETA = 0;
    private static Variables scen = null;
    private static Variables sysScen = null;
    // Public Constants
    public static final int MAP_ROWS = 64;
    public static final int MAP_COLS = 64;
    public static final int MAP_FLOORS = 8;

    // Methods
    public static ErrorLogger getErrorLogger() {
        // Display error message
        CommonDialogs.showErrorDialog(Support.ERROR_MESSAGE,
                Support.ERROR_TITLE);
        return Support.elog;
    }

    public static ErrorLogger getNonFatalLogger() {
        // Display error message
        CommonDialogs.showErrorDialog(Support.SCRIPT_ERROR_MESSAGE,
                Support.ERROR_TITLE);
        return Support.elog;
    }

    public static Variables getVariables() {
        return Support.scen;
    }

    public static Variables getSystemVariables() {
        return Support.sysScen;
    }

    public static void createVariables() {
        Support.scen = new Variables(false);
    }

    public static void createSystemVariables() {
        Support.sysScen = new Variables(true);
    }

    public static String getVersionString() {
        if (Support.isBetaModeEnabled()) {
            return "" + Support.VERSION_MAJOR + "." + Support.VERSION_MINOR
                    + "." + Support.VERSION_BUGFIX + "-dev"
                    + Support.VERSION_BETA;
        } else {
            return "" + Support.VERSION_MAJOR + "." + Support.VERSION_MINOR
                    + "." + Support.VERSION_BUGFIX;
        }
    }

    public static boolean isBetaModeEnabled() {
        return Support.VERSION_BETA > 0;
    }
}
