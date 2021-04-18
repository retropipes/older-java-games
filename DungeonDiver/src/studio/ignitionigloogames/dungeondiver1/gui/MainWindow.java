package studio.ignitionigloogames.dungeondiver1.gui;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public final class MainWindow {
    private static MainWindow window;
    private final JFrame frame;
    private JPanel content;
    private JPanel savedContent;
    private String savedTitle;
    private boolean fixedSizeSet;
    private WindowListener wl;
    private KeyListener kl;

    private MainWindow() {
        super();
        this.frame = new JFrame();
        this.frame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.setResizable(false);
        this.content = new JPanel();
        this.savedContent = this.content;
        this.frame.setContentPane(this.content);
        this.frame.setVisible(true);
        this.savedTitle = this.frame.getTitle();
        this.fixedSizeSet = false;
    }

    public static MainWindow getMainWindow() {
        if (MainWindow.window == null) {
            MainWindow.window = new MainWindow();
        }
        return MainWindow.window;
    }

    private void clearEventHandlers() {
        if (this.wl != null) {
            this.frame.removeWindowListener(this.wl);
            this.wl = null;
        }
        if (this.kl != null) {
            this.frame.removeKeyListener(this.kl);
            this.kl = null;
        }
    }

    public void attachContent(final JPanel customContent) {
        this.clearEventHandlers();
        this.content = customContent;
        this.frame.setContentPane(this.content);
        this.frame.pack();
    }

    public void attachAndSave(final JPanel customContent) {
        this.clearEventHandlers();
        this.savedContent = this.content;
        this.content = customContent;
        this.frame.setContentPane(this.content);
        this.frame.pack();
    }

    public void restoreSaved() {
        this.clearEventHandlers();
        this.content = this.savedContent;
        this.frame.setContentPane(this.content);
        this.frame.setTitle(this.savedTitle);
    }

    public void setTitle(final String title) {
        this.savedTitle = this.frame.getTitle();
        this.frame.setTitle(title);
    }

    public void setFixedSize(final Dimension d, final JMenuBar menubar) {
        if (!this.fixedSizeSet) {
            this.frame.setJMenuBar(menubar);
            this.frame.setSize(d);
            this.frame.setPreferredSize(d);
            this.frame.setMinimumSize(d);
            this.frame.setMaximumSize(d);
            this.fixedSizeSet = true;
        }
    }

    public void addWindowListener(final WindowListener l) {
        this.wl = l;
        this.frame.addWindowListener(l);
    }

    public void addKeyListener(final KeyListener l) {
        this.kl = l;
        this.frame.addKeyListener(l);
    }

    public void setDefaultButton(final JButton defaultButton) {
        this.frame.getRootPane().setDefaultButton(defaultButton);
    }
}
