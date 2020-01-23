/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitHandler;
import java.awt.desktop.QuitResponse;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.manager.asset.LogoManager;
import studio.ignitionigloogames.chrystalz.manager.asset.MusicConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.MusicManager;
import studio.ignitionigloogames.chrystalz.manager.dungeon.DungeonManager;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.fileio.DirectoryUtilities;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public final class GUIManager implements QuitHandler {
    // Fields
    private final JFrame guiFrame;
    private final JLabel logoLabel;

    // Constructors
    public GUIManager() {
        this.guiFrame = new JFrame("Chrystalz");
        final Image iconlogo = LogoManager.getIconLogo();
        this.guiFrame.setIconImage(iconlogo);
        final Container guiPane = this.guiFrame.getContentPane();
        this.guiFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.guiFrame.setLayout(new GridLayout(1, 1));
        this.logoLabel = new JLabel("", null, SwingConstants.CENTER);
        this.logoLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
        guiPane.add(this.logoLabel);
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
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        MusicManager.playMusic(MusicConstants.MUSIC_TITLE);
        this.showGUICommon();
    }

    public void showGUIAndKeepMusic() {
        this.showGUICommon();
    }

    private void showGUICommon() {
        final Application app = Chrystalz.getApplication();
        app.setMode(Application.STATUS_GUI);
        this.guiFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        this.guiFrame.setVisible(true);
        app.getMenuManager().setMainMenus();
        app.getMenuManager().checkFlags();
    }

    public void hideGUI() {
        this.guiFrame.setVisible(false);
    }

    public void updateLogo() {
        final BufferedImageIcon logo = LogoManager.getLogo();
        this.logoLabel.setIcon(logo);
        final Image iconlogo = Application.getIconLogo();
        this.guiFrame.setIconImage(iconlogo);
        this.guiFrame.pack();
    }

    @Override
    public void handleQuitRequestWith(final QuitEvent qe,
            final QuitResponse qr) {
        final DungeonManager mm = Chrystalz.getApplication()
                .getDungeonManager();
        boolean saved = true;
        int status = JOptionPane.DEFAULT_OPTION;
        if (mm.getDirty()) {
            status = DungeonManager.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = DungeonManager.saveGame();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                mm.setDirty(false);
            }
        }
        if (saved) {
            PreferencesManager.writePrefs();
            // Run cleanup task
            try {
                final File dirToDelete = new File(
                        Dungeon.getDungeonTempFolder());
                DirectoryUtilities.removeDirectory(dirToDelete);
            } catch (final Throwable t) {
                // Ignore
            }
            qr.performQuit();
        } else {
            qr.cancelQuit();
        }
    }
}
