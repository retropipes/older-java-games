package net.dynamicdungeon.dynamicdungeon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.DungeonObjectList;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ImageTransformer;
import net.dynamicdungeon.help.GraphicalHelpViewer;
import net.dynamicdungeon.images.BufferedImageIcon;

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
            final DungeonObjectList objectList = DynamicDungeon.getApplication()
                    .getObjects();
            final String[] objectNames = objectList.getAllDescriptions();
            final BufferedImageIcon[] objectAppearances = objectList
                    .getAllEditorAppearances();
            this.hv = new GraphicalHelpViewer(objectAppearances, objectNames,
                    new Color(223, 223, 223));
            final JButton export = new JButton("Export");
            export.addActionListener(buttonHandler);
            this.helpFrame = new JFrame("DynamicDungeon Object Help");
            final Image iconlogo = Application.getIconLogo();
            this.helpFrame.setIconImage(iconlogo);
            this.helpFrame
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            this.helpFrame.setLayout(new BorderLayout());
            this.helpFrame.add(this.hv.getHelp(), BorderLayout.CENTER);
            this.helpFrame.add(export, BorderLayout.SOUTH);
            this.hv.setHelpSize(ImageTransformer.MAX_WINDOW_SIZE,
                    ImageTransformer.MAX_WINDOW_SIZE);
            this.helpFrame.pack();
            this.helpFrame.setResizable(false);
            final MenuManager menu = new MenuManager();
            menu.setHelpMenus();
            this.helpFrame.setJMenuBar(menu.getMainMenuBar());
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
