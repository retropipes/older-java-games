/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.editor.rulesets.xml;

import java.io.FileNotFoundException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.editor.rulesets.RuleSetConstants;
import com.puttysoftware.widgetwarren.maze.xml.XMLExtension;
import com.puttysoftware.xio.XDataWriter;

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
        final Application app = WidgetWarren.getApplication();
        final String sg = "Rule Set";
        // filename check
        final boolean hasExtension = XMLRuleSetSaveTask
                .hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += XMLExtension.getXMLRuleSetExtensionWithPeriod();
        }
        try (XDataWriter ruleSetFile = new XDataWriter(this.filename, "ruleset")) {
            ruleSetFile.writeInt(RuleSetConstants.MAGIC_NUMBER_2);
            app.getObjects().writeRuleSetXML(ruleSetFile);
            CommonDialogs.showTitledDialog(sg + " file saved.",
                    "Rule Set Picker");
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs
                    .showDialog("Saving the "
                            + sg.toLowerCase()
                            + " file failed, probably due to illegal characters in the file name.");
        } catch (final Exception ex) {
            WidgetWarren.getErrorLogger().logError(ex);
        }
    }

    private static boolean hasExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(i + 1).toLowerCase();
        }
        if (ext == null) {
            return false;
        } else {
            return true;
        }
    }
}
