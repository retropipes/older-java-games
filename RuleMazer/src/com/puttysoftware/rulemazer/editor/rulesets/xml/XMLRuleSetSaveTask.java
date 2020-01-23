/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.editor.rulesets.xml;

import java.io.FileNotFoundException;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.CommonDialogs;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.editor.rulesets.RuleSetConstants;
import com.puttysoftware.rulemazer.maze.xml.XMLExtension;
import com.puttysoftware.xmlio.XMLDataWriter;

public class XMLRuleSetSaveTask extends Thread {
    // Fields
    private String filename;

    // Constructors
    public XMLRuleSetSaveTask(final String file) {
        this.filename = file;
        this.setName("XML Rule Set File Writer");
    }

    @Override
    public void run() {
        final Application app = Main.getApplication();
        final String sg = "Rule Set";
        // filename check
        final boolean hasExtension = XMLRuleSetSaveTask
                .hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += XMLExtension.getXMLRuleSetExtensionWithPeriod();
        }
        try {
            final XMLDataWriter ruleSetFile = new XMLDataWriter(this.filename,
                    "ruleset");
            ruleSetFile.writeInt(RuleSetConstants.MAGIC_NUMBER_2);
            app.getObjects().writeRuleSetXML(ruleSetFile);
            ruleSetFile.close();
            CommonDialogs.showTitledDialog(sg + " file saved.",
                    "Rule Set Picker");
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs
                    .showDialog("Saving the "
                            + sg.toLowerCase()
                            + " file failed, probably due to illegal characters in the file name.");
        } catch (final Exception ex) {
            Main.getDebug().debug(ex);
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
