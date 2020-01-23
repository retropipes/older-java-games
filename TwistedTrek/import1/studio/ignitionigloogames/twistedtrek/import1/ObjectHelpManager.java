/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.help.GraphicalHelpViewer;
import studio.ignitionigloogames.images.BufferedImageIcon;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObjectList;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.GraphicsManager;

public class ObjectHelpManager {
    // Fields
    private final JFrame helpFrame;
    private final MazeObjectList objectList;
    private final String[] objectNames;
    private final BufferedImageIcon[] objectAppearances;
    private final GraphicalHelpViewer hv;

    // Constructors
    public ObjectHelpManager() {
	this.objectList = Import1.getApplication().getObjects();
	this.objectNames = this.objectList.getAllDescriptions();
	this.objectAppearances = this.objectList.getAllEditorAppearances();
	this.hv = new GraphicalHelpViewer(this.objectAppearances, this.objectNames);
	this.helpFrame = new JFrame("Import1 Object Help");
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
