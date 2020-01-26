package net.dynamicdungeon.dynamicdungeon;

import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.dynamicdungeon.dynamicdungeon.resourcemanagers.HelpManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ImageTransformer;
import net.dynamicdungeon.help.HTMLHelpViewer;

public class GeneralHelpManager {
    // Fields
    private final JFrame helpFrame;
    private final HTMLHelpViewer hv;

    // Constructors
    public GeneralHelpManager() {
        final URL helpURL = HelpManager.getHelpURL();
        this.hv = new HTMLHelpViewer(helpURL);
        this.helpFrame = new JFrame("DynamicDungeon Help");
        this.helpFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.helpFrame.setLayout(new FlowLayout());
        this.helpFrame.add(this.hv.getHelp());
        this.hv.setHelpSize(ImageTransformer.MAX_WINDOW_SIZE,
                ImageTransformer.MAX_WINDOW_SIZE);
        this.helpFrame.pack();
        this.helpFrame.setResizable(false);
    }

    // Methods
    public void showHelp() {
        this.helpFrame.setVisible(true);
    }
}