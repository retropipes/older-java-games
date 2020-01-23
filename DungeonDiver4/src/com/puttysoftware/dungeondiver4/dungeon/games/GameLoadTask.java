/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.PrefixHandler;
import com.puttysoftware.dungeondiver4.resourcemanagers.LogoManager;
import com.puttysoftware.xio.ZipUtilities;

public class GameLoadTask extends Thread {
    // Fields
    private String filename;
    private JFrame loadFrame;

    // Constructors
    public GameLoadTask(String file) {
        this.filename = file;
        this.setName("Locked File Loader");
        this.loadFrame = new JFrame("Loading...");
        this.loadFrame.setIconImage(LogoManager.getIconLogo());
        JProgressBar loadBar = new JProgressBar();
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
        this.loadFrame.setVisible(true);
        Application app = DungeonDiver4.getApplication();
        int startW;
        String sg;
        app.getGameManager().setSavedGameFlag(false);
        sg = "Dungeon";
        try {
            File dungeonFile = new File(this.filename);
            File tempLock = new File(Dungeon.getDungeonTempFolder()
                    + "lock.tmp");
            Dungeon gameDungeon = new Dungeon();
            // Unlock the file
            GameFileManager.load(dungeonFile, tempLock);
            ZipUtilities.unzipDirectory(tempLock,
                    new File(gameDungeon.getBasePath()));
            boolean success = tempLock.delete();
            if (!success) {
                throw new IOException("Failed to delete temporary file!");
            }
            // Set prefix handler
            gameDungeon.setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            gameDungeon.setSuffixHandler(null);
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
            app.getMenuManager().setGameFlag();
            app.getEditor().dungeonChanged();
            app.getGameManager().stateChanged();
            CommonDialogs.showDialog(sg + " file loaded.");
            app.getDungeonManager().handleDeferredSuccess(true);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs
                    .showDialog("Loading the "
                            + sg.toLowerCase()
                            + " file failed, probably due to illegal characters in the file name.");
            app.getDungeonManager().handleDeferredSuccess(false);
        } catch (final IOException ie) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " file failed, due to some other type of I/O error.");
            app.getDungeonManager().handleDeferredSuccess(false);
        } catch (final Exception ex) {
            DungeonDiver4.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
