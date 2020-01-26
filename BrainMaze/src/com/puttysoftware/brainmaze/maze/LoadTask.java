/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.resourcemanagers.LogoManager;
import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fileutils.ZipUtilities;

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
        final Application app = BrainMaze.getApplication();
        int startW;
        String sg;
        if (this.isSavedGame) {
            app.getGameManager().setSavedGameFlag(true);
            sg = "Saved Game";
        } else {
            app.getGameManager().setSavedGameFlag(false);
            sg = "Maze";
        }
        try {
            final File mazeFile = new File(this.filename);
            Maze gameMaze = new Maze();
            ZipUtilities.unzipDirectory(mazeFile,
                    new File(gameMaze.getBasePath()));
            // Set prefix handler
            gameMaze.setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            if (this.isSavedGame) {
                gameMaze.setSuffixHandler(new SuffixHandler());
            } else {
                gameMaze.setSuffixHandler(null);
            }
            gameMaze = gameMaze.readMaze();
            if (gameMaze == null) {
                throw new IOException("Unknown object encountered.");
            }
            app.getMazeManager().setMaze(gameMaze);
            startW = gameMaze.getStartLevel();
            gameMaze.switchLevel(startW);
            final boolean playerExists = gameMaze.doesPlayerExist();
            if (playerExists) {
                gameMaze.setPlayerToStart();
                app.getGameManager().resetViewingWindow();
            }
            if (!this.isSavedGame) {
                gameMaze.save();
            }
            // Final cleanup
            final String lum = app.getMazeManager().getLastUsedMaze();
            final String lug = app.getMazeManager().getLastUsedGame();
            app.getMazeManager().clearLastUsedFilenames();
            if (this.isSavedGame) {
                app.getMazeManager().setLastUsedGame(lug);
            } else {
                app.getMazeManager().setLastUsedMaze(lum);
            }
            app.getMenuManager().clearGameFlag();
            app.getEditor().mazeChanged();
            app.getGameManager().stateChanged();
            CommonDialogs.showDialog(sg + " file loaded.");
            app.getMazeManager().handleDeferredSuccess(true);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs
                    .showDialog("Loading the "
                            + sg.toLowerCase()
                            + " file failed, probably due to illegal characters in the file name.");
            app.getMazeManager().handleDeferredSuccess(false);
        } catch (final IOException ie) {
            CommonDialogs.showDialog("Error loading " + sg.toLowerCase()
                    + " file: " + ie.getMessage());
            app.getMazeManager().handleDeferredSuccess(false);
        } catch (final Exception ex) {
            BrainMaze.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
