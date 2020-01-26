/*  WidgetWarren: A RuleSet-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.editor.rulesets;

import java.io.File;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fileutils.FilenameChecker;
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.editor.rulesets.xml.XMLRuleSetFilter;
import com.puttysoftware.widgetwarren.editor.rulesets.xml.XMLRuleSetLoadTask;
import com.puttysoftware.widgetwarren.editor.rulesets.xml.XMLRuleSetSaveTask;
import com.puttysoftware.widgetwarren.maze.xml.XMLExtension;
import com.puttysoftware.widgetwarren.security.SandboxManager;

public class RuleSetManager {
    // Constructors
    private RuleSetManager() {
        // Do nothing
    }

    // Methods
    public static void importRuleSet() {
        final Application app = WidgetWarren.getApplication();
        String filename, extension;
        final XMLRuleSetFilter xrsf = new XMLRuleSetFilter();
        final File file = CommonDialogs.showFileOpenDialog(new File(
                SandboxManager.getSandboxManager().getDocumentsDirectory()),
                xrsf, "Import Rule Set");
        if (file != null) {
            filename = file.getAbsolutePath();
            extension = RuleSetManager.getExtension(file);
            app.getGameManager().resetObjectInventory();
            if (extension.equals(XMLExtension.getXMLRuleSetExtension())) {
                RuleSetManager.importFile(filename);
            } else {
                CommonDialogs.showDialog(
                        "You opened something other than a rule set file. Select a rule set file, and try again.");
            }
        }
    }

    private static void importFile(final String filename) {
        if (!FilenameChecker
                .isFilenameOK(RuleSetManager.getNameWithoutExtension(
                        RuleSetManager.getFileNameOnly(filename)))) {
            CommonDialogs.showErrorDialog(
                    "The file you selected contains illegal characters in its\n"
                            + "name. These characters are not allowed: /?<>\\:|\"\n"
                            + "Files named con, nul, or prn are illegal, as are files\n"
                            + "named com1 through com9 and lpt1 through lpt9.",
                    "Load");
        } else {
            final XMLRuleSetLoadTask xrslt = new XMLRuleSetLoadTask(filename);
            xrslt.start();
        }
    }

    public static boolean exportRuleSet() {
        String filename = "";
        String fileOnly = "\\";
        String extension;
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final File file = CommonDialogs.showFileSaveDialog(new File(
                    SandboxManager.getSandboxManager().getDocumentsDirectory()),
                    "Export Rule Set");
            if (file != null) {
                extension = RuleSetManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = SandboxManager.getSandboxManager()
                        .getDocumentsDirectory();
                fileOnly = filename.substring(dirOnly.length() + 1);
                if (!FilenameChecker.isFilenameOK(fileOnly)) {
                    CommonDialogs.showErrorDialog(
                            "The file name you entered contains illegal characters.\n"
                                    + "These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Save");
                } else {
                    if (extension != null) {
                        if (!extension.equals(
                                XMLExtension.getXMLRuleSetExtension())) {
                            filename = RuleSetManager
                                    .getNameWithoutExtension(file)
                                    + XMLExtension
                                            .getXMLRuleSetExtensionWithPeriod();
                        }
                    } else {
                        filename += XMLExtension
                                .getXMLRuleSetExtensionWithPeriod();
                    }
                    RuleSetManager.exportFile(filename);
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static void exportFile(final String filename) {
        final XMLRuleSetSaveTask xrsst = new XMLRuleSetSaveTask(filename);
        xrsst.start();
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getNameWithoutExtension(final File f) {
        String ext = null;
        final String s = f.getAbsolutePath();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getNameWithoutExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getFileNameOnly(final String s) {
        String fno = null;
        final int i = s.lastIndexOf(File.separatorChar);
        if (i > 0 && i < s.length() - 1) {
            fno = s.substring(i + 1);
        } else {
            fno = s;
        }
        return fno;
    }
}
