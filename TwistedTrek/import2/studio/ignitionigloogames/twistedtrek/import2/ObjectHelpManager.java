/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.help.GraphicalHelpViewer;
import studio.ignitionigloogames.images.BufferedImageIcon;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.MazeObjectList;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ImageTransformer;

public class ObjectHelpManager {
    // Fields
    private JFrame helpFrame;
    GraphicalHelpViewer hv;
    private boolean inited = false;

    // Constructors
    public ObjectHelpManager() {
	// Do nothing
    }

    // Methods
    public void showHelp() {
	this.initHelp();
	this.helpFrame.setVisible(true);
    }

    private void initHelp() {
	if (!this.inited) {
	    final ButtonHandler buttonHandler = new ButtonHandler();
	    final MazeObjectList objectList = Import2.getApplication().getObjects();
	    final String[] objectNames = objectList.getAllDescriptions();
	    final BufferedImageIcon[] objectAppearances = objectList.getAllEditorAppearances();
	    this.hv = new GraphicalHelpViewer(objectAppearances, objectNames);
	    final JButton export = new JButton("Export");
	    export.addActionListener(buttonHandler);
	    this.helpFrame = new JFrame("Import2 Object Help");
	    final Image iconlogo = Application.getIconLogo();
	    this.helpFrame.setIconImage(iconlogo);
	    this.helpFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	    this.helpFrame.setLayout(new BorderLayout());
	    this.helpFrame.add(this.hv.getHelp(), BorderLayout.CENTER);
	    this.helpFrame.add(export, BorderLayout.SOUTH);
	    this.hv.setHelpSize(ImageTransformer.MAX_WINDOW_SIZE, ImageTransformer.MAX_WINDOW_SIZE);
	    this.helpFrame.pack();
	    this.helpFrame.setResizable(false);
	    // Mac OS X-specific fixes
	    if (System.getProperty("os.name").startsWith("Mac OS X")) {
		final MenuManager menu = new MenuManager();
		menu.setHelpMenus();
		this.helpFrame.setJMenuBar(menu.getMainMenuBar());
	    }
	    this.inited = true;
	}
    }

    private class ButtonHandler implements ActionListener {
	ButtonHandler() {
	    // Do nothing
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
	    ObjectHelpManager.this.hv.exportHelp();
	}
    }
}
