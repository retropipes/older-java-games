/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support;

import java.io.File;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.fileutils.DirectoryUtilities;
import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.scenario.Scenario;
import com.puttysoftware.updater.ProductData;

public class Support {
    // Constants
    private static final String PROGRAM_NAME = "Gemma";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Email bug reports to: support@puttysoftware.com\n"
            + "Subject: Gemma Bug Report";
    private static final String SCRIPT_ERROR_MESSAGE = "A problem has occurred while running a script.\n"
            + "This error is non-fatal, and has been logged.";
    private static final String ERROR_TITLE = "Gemma Error";
    private static final String NF_ERROR_TITLE = "Gemma Script Error";
    private static final ErrorLogger elog = new ErrorLogger(
            Support.PROGRAM_NAME);
    private static final int VERSION_MAJOR = 2;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_CODE = ProductData.CODE_STABLE;
    private static final int VERSION_PRERELEASE = 0;
    private static Scenario scen = null;
    private static final int BATTLE_MAP_SIZE = 16;
    private static final int BATTLE_MAP_FLOOR_SIZE = 1;
    private static final int GAME_MAP_SIZE = 256;
    private static final int GAME_MAP_FLOOR_SIZE = 1;
    private static final boolean debugMode = false;
    private static final ProductData pd = new ProductData(VERSION_MAJOR,
            VERSION_MINOR, VERSION_BUGFIX, VERSION_CODE,
            VERSION_PRERELEASE);

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
                Support.NF_ERROR_TITLE + suffix);
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

    public static int getBattleMapFloorSize() {
        return Support.BATTLE_MAP_FLOOR_SIZE;
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

    public static String getVersionString() {
        return pd.getVersionString();
    }
}
