package studio.ignitionigloogames.dungeondiver1;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

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
        this.menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
