/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue;

import java.io.File;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.fileutils.DirectoryUtilities;
import com.puttysoftware.riskyrescue.creatures.Creature;
import com.puttysoftware.riskyrescue.scenario.Scenario;

public class Support {
    // Constants
    private static final String PROGRAM_NAME = "RiskyRescue";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Report bugs at the project's GitHub issue tracker:\n"
            + "https://github.com/PuttySoftware/risky-rescue/issues/new";
    private static final String SCRIPT_ERROR_MESSAGE = "A problem has occurred while running a script.\n"
            + "This problem has been logged.";
    private static final String ERROR_TITLE = "RiskyRescue Error";
    private static final String NF_ERROR_TITLE = "RiskyRescue Script Error";
    private static final ErrorLogger elog = new ErrorLogger(
            Support.PROGRAM_NAME);
    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 1;
    private static final int VERSION_BUGFIX = 0;
    private static Scenario scen = null;
    private static final int BATTLE_MAP_SIZE = 9;
    private static final int BATTLE_MAP_SIZE_DEBUG = 9;
    private static final int BATTLE_MAP_FLOOR_SIZE = 1;
    private static final int BATTLE_MAP_FLOOR_SIZE_DEBUG = 1;
    private static final int GAME_MAP_SIZE = 64;
    private static final int GAME_MAP_SIZE_DEBUG = 16;
    private static final int GAME_MAP_FLOOR_SIZE = 1;
    private static final int GAME_MAP_FLOOR_SIZE_DEBUG = 1;
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
        if (Support.inDebugMode()) {
            return Support.BATTLE_MAP_SIZE_DEBUG;
        }
        return Support.BATTLE_MAP_SIZE;
    }

    public static int getBattleMapFloorSize() {
        if (Support.inDebugMode()) {
            return Support.BATTLE_MAP_FLOOR_SIZE_DEBUG;
        }
        return Support.BATTLE_MAP_FLOOR_SIZE;
    }

    public static int getGameMapSize() {
        if (Support.inDebugMode()) {
            return Support.GAME_MAP_SIZE_DEBUG;
        }
        return Support.GAME_MAP_SIZE;
    }

    public static int getGameMapFloorSize() {
        if (Support.inDebugMode()) {
            return Support.GAME_MAP_FLOOR_SIZE_DEBUG;
        }
        return Support.GAME_MAP_FLOOR_SIZE;
    }

    public static void preInit() {
        // Compute action cap
        Creature.computeActionCap(Support.BATTLE_MAP_SIZE,
                Support.BATTLE_MAP_SIZE);
    }

    public static String getVersionString() {
        return "" + Support.VERSION_MAJOR + "." + Support.VERSION_MINOR + "."
                + Support.VERSION_BUGFIX;
    }
}
