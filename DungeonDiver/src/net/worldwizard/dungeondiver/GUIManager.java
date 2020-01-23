package net.worldwizard.dungeondiver;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUIManager {
    // Fields
    private final JFrame menuFrame;
    private final JLabel logoLabel;
    private final MenuManager mgr;

    // Constructors
    public GUIManager() {
        // Initialize GUI
        this.mgr = new MenuManager();
        this.menuFrame = new JFrame("Dungeon Diver");
        this.menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menuFrame.setJMenuBar(this.mgr.getMenuBar());
        this.logoLabel = new JLabel(GraphicsManager.getLogo());
        this.menuFrame.getContentPane().add(this.logoLabel);
        this.menuFrame.setResizable(false);
        this.menuFrame.pack();
    }

    // Methods
    public JFrame getParentFrame() {
        return this.menuFrame;
    }

    public void showGUI() {
        this.menuFrame.setVisible(true);
    }

    public void hideGUI() {
        this.menuFrame.setVisible(false);
    }
}
