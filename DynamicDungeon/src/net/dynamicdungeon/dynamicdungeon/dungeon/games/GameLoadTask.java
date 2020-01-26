/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.VersionException;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.PrefixHandler;
import net.dynamicdungeon.dynamicdungeon.dungeon.SuffixHandler;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractDungeonObject;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.ImageColorConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.LogoManager;
import net.dynamicdungeon.fileutils.ZipUtilities;

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
            final Application app = DynamicDungeon.getApplication();
            int startW;
            app.getGameManager().setSavedGameFlag(false);
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
                app.getGameManager().resetViewingWindow();
            }
            gameDungeon.save();
            // Final cleanup
            app.getGameManager().stateChanged();
            AbstractDungeonObject
                    .setTemplateColor(ImageColorConstants.getColorForLevel(
                            PartyManager.getParty().getDungeonLevel()));
            app.getDungeonManager().setLoaded(true);
            CommonDialogs.showDialog(sg + " loaded.");
            app.getGameManager().playDungeon();
            app.getDungeonManager().handleDeferredSuccess(true, false, null);
        } catch (final VersionException ve) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, due to the format version being unsupported.");
            DynamicDungeon.getApplication().getDungeonManager()
                    .handleDeferredSuccess(false, true, mazeFile);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, probably due to illegal characters in the file name.");
            DynamicDungeon.getApplication().getDungeonManager()
                    .handleDeferredSuccess(false, false, null);
        } catch (final IOException ie) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, due to some other type of I/O error.");
            DynamicDungeon.getApplication().getDungeonManager()
                    .handleDeferredSuccess(false, false, null);
        } catch (final Exception ex) {
            DynamicDungeon.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
