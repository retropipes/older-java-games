/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.VersionException;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.ImageColorConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.LogoManager;
import studio.ignitionigloogames.chrystalz.manager.dungeon.PrefixHandler;
import studio.ignitionigloogames.chrystalz.manager.dungeon.SuffixHandler;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;
import studio.ignitionigloogames.common.fileio.ZipUtilities;

public class GameLoadTask extends Thread {
    // Fields
    private final String filename;
    private final JFrame loadFrame;

    // Constructors
    public GameLoadTask(final String file) {
        this.filename = file;
        this.setName("Game Loader");
        this.loadFrame = new JFrame("Loading...");
        this.loadFrame.setIconImage(LogoManager.getIconLogo());
        final JProgressBar loadBar = new JProgressBar();
        loadBar.setIndeterminate(true);
        this.loadFrame.getContentPane().add(loadBar);
        this.loadFrame.setResizable(false);
        this.loadFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.loadFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        final String sg = "Game";
        final File mazeFile = new File(this.filename);
        try {
            this.loadFrame.setVisible(true);
            final Application app = Chrystalz.getApplication();
            int startW;
            app.getGame().setSavedGameFlag(false);
            final File tempLock = new File(
                    Dungeon.getDungeonTempFolder() + "lock.tmp");
            Dungeon gameDungeon = new Dungeon();
            // Unlock the file
            GameFileManager.load(mazeFile, tempLock);
            ZipUtilities.unzipDirectory(tempLock,
                    new File(gameDungeon.getBasePath()));
            final boolean success = tempLock.delete();
            if (!success) {
                throw new IOException("Failed to delete temporary file!");
            }
            // Set prefix handler
            gameDungeon.setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            gameDungeon.setSuffixHandler(new SuffixHandler());
            gameDungeon = gameDungeon.readDungeon();
            if (gameDungeon == null) {
                throw new IOException("Unknown object encountered.");
            }
            app.getDungeonManager().setDungeon(gameDungeon);
            startW = gameDungeon.getStartLevel();
            gameDungeon.switchLevel(startW);
            final boolean playerExists = gameDungeon.doesPlayerExist();
            if (playerExists) {
                app.getDungeonManager().getDungeon().setPlayerToStart();
                app.getGame().resetViewingWindow();
            }
            gameDungeon.save();
            // Final cleanup
            app.getGame().stateChanged();
            AbstractGameObject.setTemplateColor(ImageColorConstants
                    .getColorForLevel(PartyManager.getParty().getZone()));
            app.getDungeonManager().setLoaded(true);
            CommonDialogs.showDialog(sg + " loaded.");
            app.getGame().playDungeon();
            app.getDungeonManager().handleDeferredSuccess(true, false, null);
        } catch (final VersionException ve) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, due to the format version being unsupported.");
            Chrystalz.getApplication().getDungeonManager()
                    .handleDeferredSuccess(false, true, mazeFile);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, probably due to illegal characters in the file name.");
            Chrystalz.getApplication().getDungeonManager()
                    .handleDeferredSuccess(false, false, null);
        } catch (final IOException ie) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, due to some other type of I/O error.");
            Chrystalz.getApplication().getDungeonManager()
                    .handleDeferredSuccess(false, false, null);
        } catch (final Exception ex) {
            Chrystalz.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
