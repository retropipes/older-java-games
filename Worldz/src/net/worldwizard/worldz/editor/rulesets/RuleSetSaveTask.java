/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.editor.rulesets;

import java.io.FileNotFoundException;

import net.worldwizard.io.DataConstants;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.world.Extension;

public class RuleSetSaveTask extends Thread {
    // Fields
    private String filename;

    // Constructors
    public RuleSetSaveTask(final String file) {
        this.filename = file;
        this.setName("Rule Set File Writer");
    }

    @Override
    public void run() {
        final Application app = Worldz.getApplication();
        final String sg = "Rule Set";
        // filename check
        final boolean hasExtension = RuleSetSaveTask
                .hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += Extension.getRuleSetExtensionWithPeriod();
        }
        try {
            final DataWriter worldFile = new DataWriter(this.filename,
                    DataConstants.DATA_MODE_BINARY);
            app.getObjects().writeRuleSet(worldFile);
            worldFile.close();
            Messager.showTitledDialog(sg + " file saved.", "Rule Set Picker");
        } catch (final FileNotFoundException fnfe) {
            Messager.showDialog("Writing the " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
        } catch (final Exception ex) {
            Worldz.getDebug().debug(ex);
        }
    }

    private static boolean hasExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        if (ext == null) {
            return false;
        } else {
            return true;
        }
    }
}
