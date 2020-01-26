/*  Worldz: A RuleSet-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.editor.rulesets;

import java.io.File;

import javax.swing.JFileChooser;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.world.Extension;
import net.worldwizard.worldz.world.FilenameChecker;

public class RuleSetManager {
    // Constructors
    private RuleSetManager() {
        // Do nothing
    }

    // Methods
    public static void loadRuleSet() {
        final Application app = Worldz.getApplication();
        String filename, extension;
        final JFileChooser fc = new JFileChooser();
        final RuleSetFilter rsf = new RuleSetFilter();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(rsf);
        final int returnVal = fc.showOpenDialog(app.getOutputFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File file = fc.getSelectedFile();
            filename = file.getAbsolutePath();
            extension = RuleSetManager.getExtension(file);
            app.getGameManager().resetObjectInventory();
            if (extension.equals(Extension.getRuleSetExtension())) {
                RuleSetManager.loadFile(filename);
            } else {
                Messager.showDialog(
                        "You opened something other than a rule set file. Select a rule set file, and try again.");
            }
        }
    }

    private static void loadFile(final String filename) {
        if (!FilenameChecker
                .isFilenameOK(RuleSetManager.getNameWithoutExtension(
                        RuleSetManager.getFileNameOnly(filename)))) {
            Messager.showErrorDialog(
                    "The file you selected contains illegal characters in its\n"
                            + "name. These characters are not allowed: /?<>\\:|\"\n"
                            + "Files named con, nul, or prn are illegal, as are files\n"
                            + "named com1 through com9 and lpt1 through lpt9.",
                    "Load");
        } else {
            final RuleSetLoadTask rslt = new RuleSetLoadTask(filename);
            rslt.start();
        }
    }

    public static boolean saveRuleSet() {
        final Application app = Worldz.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final JFileChooser fc = new JFileChooser();
        final RuleSetFilter rsf = new RuleSetFilter();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(rsf);
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final int returnVal = fc.showSaveDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                extension = RuleSetManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = fc.getCurrentDirectory()
                        .getAbsolutePath();
                fileOnly = filename.substring(dirOnly.length() + 1);
                if (!FilenameChecker.isFilenameOK(fileOnly)) {
                    Messager.showErrorDialog(
                            "The file name you entered contains illegal characters.\n"
                                    + "These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Save");
                } else {
                    if (extension != null) {
                        if (!extension
                                .equals(Extension.getRuleSetExtension())) {
                            filename = RuleSetManager
                                    .getNameWithoutExtension(file)
                                    + Extension.getRuleSetExtensionWithPeriod();
                        }
                    } else {
                        filename += Extension.getRuleSetExtensionWithPeriod();
                    }
                    RuleSetManager.saveFile(filename);
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static void saveFile(final String filename) {
        final RuleSetSaveTask rsst = new RuleSetSaveTask(filename);
        rsst.start();
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
