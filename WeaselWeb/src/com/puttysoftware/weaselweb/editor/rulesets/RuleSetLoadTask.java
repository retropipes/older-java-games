/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.editor.rulesets;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.xio.XDataReader;

public class RuleSetLoadTask extends Thread {
    // Fields
    private final String filename;

    // Constructors
    public RuleSetLoadTask(final String file) {
        this.filename = file;
        this.setName(" Rule Set File Reader");
    }

    // Methods
    @Override
    public void run() {
        final Application app = WeaselWeb.getApplication();
        final String sg = "Rule Set";
        try {
            final XDataReader ruleSetFile = new XDataReader(this.filename,
                    "ruleset");
            try {
                final int magic = ruleSetFile.readInt();
                if (magic == RuleSetConstants.MAGIC_NUMBER_2) {
                    // Format 2 file
                    app.getObjects().readRuleSet(ruleSetFile,
                            RuleSetConstants.FORMAT_2);
                }
                ruleSetFile.close();
                CommonDialogs.showTitledDialog(sg + " file loaded.",
                        "Rule Set Picker");
            } catch (final FileNotFoundException fnfe) {
                CommonDialogs
                        .showDialog("Loading the "
                                + sg.toLowerCase()
                                + " file failed, probably due to illegal characters in the file name.");
                app.getMazeManager().handleDeferredSuccess(false);
            } catch (final IOException ie) {
                throw new InvalidRuleSetException("Error loading "
                        + sg.toLowerCase() + " file.");
            }
        } catch (final InvalidRuleSetException irse) {
            CommonDialogs.showDialog(irse.getMessage());
        } catch (final Exception ex) {
            WeaselWeb.getErrorLogger().logError(ex);
        }
    }
}
