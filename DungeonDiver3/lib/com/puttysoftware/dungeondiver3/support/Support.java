/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support;

import java.io.File;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.support.creatures.Creature;
import com.puttysoftware.dungeondiver3.support.scenario.Scenario;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.updater.ProductData;
import com.puttysoftware.xio.DirectoryUtilities;

public class Support {
    // Constants
    private static final String PROGRAM_NAME = "DungeonDiver3";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: DungeonDiver3 Bug Report";
    private static final String SCRIPT_ERROR_MESSAGE = "A problem has occurred while running a script.\n"
            + "This error is non-fatal, and has been logged.";
    private static final String ERROR_TITLE = "DungeonDiver3 Error";
    private static final ErrorLogger elog = new ErrorLogger(
            Support.PROGRAM_NAME);
    private static final int VERSION_MAJOR = 2;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_CODE = ProductData.CODE_STABLE_RELEASE;
    private static final int VERSION_PRERELEASE = 0;
    private static Scenario scen = null;
    private static final int BATTLE_MAP_SIZE = 16;
    private static final int GAME_MAP_SIZE = 64;
    private static final int GAME_MAP_FLOOR_SIZE = 8;
    private static final boolean debugMode = false;

    // Methods
    public static ErrorLogger getErrorLogger() {
        String suffix;
        if (Support.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Display error message
        CommonDialogs.showErrorDialog(Support.ERROR_MESSAGE,
                Support.ERROR_TITLE + suffix);
        return Support.elog;
    }

    public static ErrorLogger getNonFatalLogger() {
        String suffix;
        if (Support.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Display error message
        CommonDialogs.showErrorDialog(Support.SCRIPT_ERROR_MESSAGE,
                Support.ERROR_TITLE + suffix);
        return Support.elog;
    }

    public static boolean inDebugMode() {
        return Support.debugMode;
    }

    public static Scenario getScenario() {
        return Support.scen;
    }

    public static void deleteScenario() {
        final File scenFile = new File(Support.scen.getBasePath());
        if (scenFile.isDirectory() && scenFile.exists()) {
            try {
                DirectoryUtilities.removeDirectory(scenFile);
            } catch (final Throwable t) {
                // Ignore
            }
        }
    }

    public static void createScenario() {
        Support.scen = new Scenario();
    }

    public static int getBattleMapSize() {
        return Support.BATTLE_MAP_SIZE;
    }

    public static int getGameMapSize() {
        return Support.GAME_MAP_SIZE;
    }

    public static int getGameMapFloorSize() {
        return Support.GAME_MAP_FLOOR_SIZE;
    }

    public static void preInit() {
        // Compute action cap
        Creature.computeActionCap(Support.BATTLE_MAP_SIZE,
                Support.BATTLE_MAP_SIZE);
    }

    public static String getReleaseType() {
        final int code = Support.VERSION_CODE;
        String rt;
        if (code == ProductData.CODE_PRE_ALPHA) {
            rt = "dev";
        } else if (code == ProductData.CODE_ALPHA) {
            rt = "alpha";
        } else if (code == ProductData.CODE_BETA) {
            rt = "beta";
        } else if (code == ProductData.CODE_RELEASE_CANDIDATE) {
            rt = "rc";
        } else {
            rt = "";
        }
        return rt;
    }

    public static String getVersionString() {
        if (Support.isBetaModeEnabled()) {
            final int code = Support.VERSION_CODE;
            String rt;
            if (code == ProductData.CODE_PRE_ALPHA) {
                rt = "-dev";
            } else if (code == ProductData.CODE_ALPHA) {
                rt = "-alpha";
            } else if (code == ProductData.CODE_BETA) {
                rt = "-beta";
            } else if (code == ProductData.CODE_RELEASE_CANDIDATE) {
                rt = "-RC";
            } else {
                rt = "";
            }
            return "" + Support.VERSION_MAJOR + "." + Support.VERSION_MINOR
                    + "." + Support.VERSION_BUGFIX + rt
                    + Support.VERSION_PRERELEASE;
        } else {
            return "" + Support.VERSION_MAJOR + "." + Support.VERSION_MINOR
                    + "." + Support.VERSION_BUGFIX;
        }
    }

    public static boolean isBetaModeEnabled() {
        final int code = Support.VERSION_CODE;
        return code != ProductData.CODE_STABLE_RELEASE;
    }
}
