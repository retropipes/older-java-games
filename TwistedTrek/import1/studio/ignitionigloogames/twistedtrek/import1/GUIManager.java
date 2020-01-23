/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import studio.ignitionigloogames.images.BufferedImageIcon;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.GraphicsManager;

public class GUIManager {
    // Fields
    private final JFrame guiFrame;
    private final Container guiPane;
    private final JLabel logoLabel;

    // Constructors
    public GUIManager() {
	this.guiFrame = new JFrame("Import1");
	this.guiPane = this.guiFrame.getContentPane();
	this.guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	this.guiFrame.setLayout(new GridLayout(1, 1));
	this.logoLabel = new JLabel("", null, SwingConstants.CENTER);
	this.logoLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
	this.guiPane.add(this.logoLabel);
	this.guiFrame.pack();
	this.guiFrame.setResizable(false);
    }

    // Methods
    public JFrame getGUIFrame() {
	if (this.guiFrame.isVisible()) {
	    return this.guiFrame;
	} else {
	    return null;
	}
    }

    public void showGUI() {
	final Application app = Import1.getApplication();
	app.setInGUI(true);
	this.guiFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
	this.guiFrame.setVisible(true);
	app.getMenuManager().setMainMenus();
	app.getMenuManager().checkFlags();
    }

    public void hideGUI() {
	final Application app = Import1.getApplication();
	app.setInGUI(false);
	this.guiFrame.setVisible(false);
    }

    public void hideGUITemporarily() {
	this.guiFrame.setVisible(false);
    }

    public void updateLogo() {
	final BufferedImageIcon logo = GraphicsManager.getLogo();
	this.logoLabel.setIcon(logo);
	this.guiFrame.pack();
    }
}
