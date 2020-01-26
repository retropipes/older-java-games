/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.resourcemanagers.LogoManager;
import com.puttysoftware.xio.ZipUtilities;

public class LoadTask extends Thread {
    // Fields
    private final String filename;
    private final boolean isSavedGame;
    private final JFrame loadFrame;

    // Constructors
    public LoadTask(final String file, final boolean saved) {
        this.filename = file;
        this.isSavedGame = saved;
        this.setName("File Loader");
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
        this.loadFrame.setVisible(true);
        final Application app = DungeonDiver4.getApplication();
        int startW;
        String sg;
        if (this.isSavedGame) {
            app.getGameManager().setSavedGameFlag(true);
            sg = "Saved Game";
        } else {
            app.getGameManager().setSavedGameFlag(false);
            sg = "Dungeon";
        }
        try {
            final File dungeonFile = new File(this.filename);
            Dungeon gameDungeon = new Dungeon();
            ZipUtilities.unzipDirectory(dungeonFile,
                    new File(gameDungeon.getBasePath()));
            // Set prefix handler
            gameDungeon.setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            if (this.isSavedGame) {
                gameDungeon.setSuffixHandler(new SuffixHandler());
            } else {
                gameDungeon.setSuffixHandler(null);
            }
            gameDungeon = gameDungeon.readDungeon();
            if (gameDungeon == null) {
                throw new IOException("Unknown object encountered.");
            }
            app.getDungeonManager().setDungeon(gameDungeon);
            startW = gameDungeon.getStartLevel();
            gameDungeon.switchLevel(startW);
            final boolean playerExists = gameDungeon.doesPlayerExist();
            if (playerExists) {
                gameDungeon.setPlayerToStart();
                app.getGameManager().resetViewingWindow();
            }
            if (!this.isSavedGame) {
                gameDungeon.save();
            }
            // Final cleanup
            final String lum = app.getDungeonManager().getLastUsedDungeon();
            final String lug = app.getDungeonManager().getLastUsedGame();
            app.getDungeonManager().clearLastUsedFilenames();
            if (this.isSavedGame) {
                app.getDungeonManager().setLastUsedGame(lug);
            } else {
                app.getDungeonManager().setLastUsedDungeon(lum);
            }
            app.getMenuManager().clearGameFlag();
            app.getEditor().dungeonChanged();
            app.getGameManager().stateChanged();
            CommonDialogs.showDialog(sg + " file loaded.");
            app.getDungeonManager().handleDeferredSuccess(true);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
            app.getDungeonManager().handleDeferredSuccess(false);
        } catch (final IOException ie) {
            CommonDialogs.showDialog("Error loading " + sg.toLowerCase()
                    + " file: " + ie.getMessage());
            app.getDungeonManager().handleDeferredSuccess(false);
        } catch (final Exception ex) {
            DungeonDiver4.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
