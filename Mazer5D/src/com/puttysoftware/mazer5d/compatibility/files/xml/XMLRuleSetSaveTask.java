/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.files.xml;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GameObjects;
import com.puttysoftware.mazer5d.editor.rulesets.RuleSetConstants;
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
        final String sg = "Rule Set";
        // filename check
        final boolean hasExtension = XMLRuleSetSaveTask
                .hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += XMLExtension.getXMLRuleSetExtensionWithPeriod();
        }
        try (XDataWriter ruleSetFile = new XDataWriter(this.filename,
                "ruleset")) {
            ruleSetFile.writeInt(RuleSetConstants.MAGIC_NUMBER_2);

            GameObjects.writeRuleSet(ruleSetFile);
            CommonDialogs.showTitledDialog(sg + " file saved.",
                    "Rule Set Picker");
        } catch (final IOException e) {
            Mazer5D.logError(e);
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
