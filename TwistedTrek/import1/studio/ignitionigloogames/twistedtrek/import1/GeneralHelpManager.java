/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1;

import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.help.HTMLHelpViewer;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.GraphicsManager;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.HelpManager;

public class GeneralHelpManager {
    // Fields
    private final JFrame helpFrame;
    private final HTMLHelpViewer hv;

    // Constructors
    public GeneralHelpManager() {
	final URL helpURL = HelpManager.getHelpURL();
	this.hv = new HTMLHelpViewer(helpURL);
	this.helpFrame = new JFrame("Import1 Help");
	this.helpFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	this.helpFrame.setLayout(new FlowLayout());
	this.helpFrame.add(this.hv.getHelp());
	if (Import1.getApplication().getPrefsManager().isMobileModeEnabled()) {
	    this.hv.setHelpSize(GraphicsManager.MAX_MOBILE_WINDOW_SIZE, GraphicsManager.MAX_MOBILE_WINDOW_SIZE);
	} else {
	    this.hv.setHelpSize(GraphicsManager.MAX_DESKTOP_WINDOW_SIZE, GraphicsManager.MAX_DESKTOP_WINDOW_SIZE);
	}
	this.helpFrame.pack();
	this.helpFrame.setResizable(false);
    }

    // Methods
    public void showHelp() {
	this.helpFrame.setVisible(true);
    }

    public void updateHelpSize() {
	if (Import1.getApplication().getPrefsManager().isMobileModeEnabled()) {
	    this.hv.setHelpSize(GraphicsManager.MAX_MOBILE_WINDOW_SIZE, GraphicsManager.MAX_MOBILE_WINDOW_SIZE);
	} else {
	    this.hv.setHelpSize(GraphicsManager.MAX_DESKTOP_WINDOW_SIZE, GraphicsManager.MAX_DESKTOP_WINDOW_SIZE);
	}
	this.helpFrame.pack();
    }
}