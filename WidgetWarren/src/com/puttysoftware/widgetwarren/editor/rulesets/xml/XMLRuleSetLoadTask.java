/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.editor.rulesets.xml;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.editor.rulesets.RuleSetConstants;
import com.puttysoftware.xio.XDataReader;

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
        final Application app = WidgetWarren.getApplication();
        final String sg = "Rule Set";
        try (XDataReader ruleSetFile = new XDataReader(this.filename,
                "ruleset")) {
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
            CommonDialogs.showDialog(ie.getMessage());
        } catch (final Exception ex) {
            WidgetWarren.getErrorLogger().logError(ex);
        }
    }
}
