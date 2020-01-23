/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.editor.rulesets;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.worldwizard.io.DataConstants;
import net.worldwizard.io.DataReader;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;

public class RuleSetLoadTask extends Thread {
    // Fields
    private final String filename;

    // Constructors
    public RuleSetLoadTask(final String file) {
        this.filename = file;
        this.setName("Rule Set File Reader");
    }

    // Methods
    @Override
    public void run() {
        final Application app = Worldz.getApplication();
        final String sg = "Rule Set";
        try {
            final DataReader ruleSetFile = new DataReader(this.filename,
                    DataConstants.DATA_MODE_BINARY);
            try {
                app.getObjects().readRuleSet(ruleSetFile);
                ruleSetFile.close();
                Messager.showTitledDialog(sg + " file loaded.",
                        "Rule Set Picker");
            } catch (final FileNotFoundException fnfe) {
                Messager.showDialog("Reading the "
                        + sg.toLowerCase()
                        + " file failed, probably due to illegal characters in the file name.");
                app.getWorldManager().handleDeferredSuccess(false);
            } catch (final IOException ie) {
                throw new InvalidRuleSetException("Error reading "
                        + sg.toLowerCase() + " file.");
            }
        } catch (final InvalidRuleSetException irse) {
            Messager.showDialog(irse.getMessage());
        } catch (final Exception ex) {
            Worldz.getDebug().debug(ex);
        }
    }
}
