/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.editor.rulesets.xml;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.CommonDialogs;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.editor.rulesets.InvalidRuleSetException;
import com.puttysoftware.rulemazer.editor.rulesets.RuleSetConstants;
import com.puttysoftware.xmlio.XMLDataReader;

public class XMLRuleSetLoadTask extends Thread {
    // Fields
    private final String filename;

    // Constructors
    public XMLRuleSetLoadTask(final String file) {
        this.filename = file;
        this.setName("XML Rule Set File Reader");
    }

    // Methods
    @Override
    public void run() {
        final Application app = Main.getApplication();
        final String sg = "Rule Set";
        try {
            final XMLDataReader ruleSetFile = new XMLDataReader(this.filename,
                    "ruleset");
            try {
                final int magic = ruleSetFile.readInt();
                if (magic == RuleSetConstants.MAGIC_NUMBER_2) {
                    // Format 2 file
                    app.getObjects().readRuleSetXML(ruleSetFile,
                            RuleSetConstants.FORMAT_2);
                }
                ruleSetFile.close();
                CommonDialogs.showTitledDialog(sg + " file loaded.",
                        "Rule Set Picker");
            } catch (final FileNotFoundException fnfe) {
                CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                        + " file failed, probably due to illegal characters in the file name.");
                app.getMazeManager().handleDeferredSuccess(false);
            } catch (final IOException ie) {
                throw new InvalidRuleSetException(
                        "Error loading " + sg.toLowerCase() + " file.");
            }
        } catch (final InvalidRuleSetException irse) {
            CommonDialogs.showDialog(irse.getMessage());
        } catch (final Exception ex) {
            Main.getDebug().debug(ex);
        }
    }
}
