/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.editor.rulesets;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.xio.XDataReader;

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
        final Application app = FantastleX.getApplication();
        final String sg = "Rule Set";
        try (XDataReader ruleSetFile = new XDataReader(this.filename, "ruleset")) {
            final int magic = ruleSetFile.readInt();
            if (magic == RuleSetConstants.MAGIC_NUMBER_2) {
                // Format 2 file
                app.getObjects().readRuleSet(ruleSetFile,
                        RuleSetConstants.FORMAT_2);
            }
            CommonDialogs.showTitledDialog(sg + " file loaded.",
                    "Rule Set Picker");
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs
                    .showDialog("Loading the "
                            + sg.toLowerCase()
                            + " file failed, probably due to illegal characters in the file name.");
            app.getMazeManager().handleDeferredSuccess(false);
        } catch (final IOException ie) {
            CommonDialogs.showDialog(ie.getMessage());
            app.getMazeManager().handleDeferredSuccess(false);
        } catch (final Exception ex) {
            FantastleX.getErrorLogger().logError(ex);
        }
    }
}
