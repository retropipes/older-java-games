package studio.ignitionigloogames.dungeondiver1;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import studio.ignitionigloogames.dungeondiver1.gui.MainWindow;

public class GUIManager {
    // Fields
    private final JPanel guiContent;
    private final JLabel logoLabel;
    private final MenuManager mgr;
    private final Dimension windowSize;

    // Constructors
    public GUIManager() {
        // Initialize GUI
        this.mgr = new MenuManager();
        this.guiContent = new JPanel();
        this.guiContent.setLayout(new BorderLayout());
        this.logoLabel = new JLabel(GraphicsManager.getLogo());
        this.guiContent.add(this.logoLabel, BorderLayout.NORTH);
        this.windowSize = new Dimension(800, 800);
    }

    // Methods
    public void showGUI() {
        MainWindow main = MainWindow.getMainWindow();
        main.attachAndSave(this.guiContent);
        main.setFixedSize(this.windowSize, this.mgr.getMenuBar());
        main.setTitle("Dungeon Diver");
    }

    public void hideGUI() {
        MainWindow.getMainWindow().restoreSaved();
    }
}
