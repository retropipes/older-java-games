/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.editor.rulesets;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.CommonDialogs;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.resourcemanagers.GraphicsManager;
import com.puttysoftware.xio.XDataReader;

public class RuleSetLoadTask extends Thread {
    // Fields
    private final String filename;
    private final JFrame loadFrame;
    private final JProgressBar loadBar;

    // Constructors
    public RuleSetLoadTask(final String file) {
        this.filename = file;
        this.setName("Rule Set File Reader");
        this.loadFrame = new JFrame("Loading...");
        this.loadFrame.setIconImage(GraphicsManager.getIconLogo());
        this.loadBar = new JProgressBar();
        this.loadBar.setIndeterminate(true);
        this.loadFrame.getContentPane().add(this.loadBar);
        this.loadFrame.setResizable(false);
        this.loadFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.loadFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        this.loadFrame.setVisible(true);
        final Application app = MazeMode.getApplication();
        final String sg = "Rule Set";
        try {
            final XDataReader ruleSetFile = new XDataReader(this.filename,
                    "ruleset");
            try {
                final int magic = ruleSetFile.readInt();
                if (magic == RuleSetConstants.RULE_SET_MAGIC_NUMBER) {
                    // Format 2 file
                    app.getObjects().readRuleSetX(ruleSetFile,
                            RuleSetConstants.RULE_SET_FORMAT_1);
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
            MazeMode.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
