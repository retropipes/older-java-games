/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.ddremix.Application;
import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.VersionException;
import com.puttysoftware.ddremix.creatures.party.PartyManager;
import com.puttysoftware.ddremix.maze.Maze;
import com.puttysoftware.ddremix.maze.PrefixHandler;
import com.puttysoftware.ddremix.maze.SuffixHandler;
import com.puttysoftware.ddremix.maze.abc.AbstractMazeObject;
import com.puttysoftware.ddremix.maze.utilities.ImageColorConstants;
import com.puttysoftware.ddremix.resourcemanagers.LogoManager;
import com.puttysoftware.xio.ZipUtilities;

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
            final Application app = DDRemix.getApplication();
            int startW;
            app.getGameManager().setSavedGameFlag(false);
            final File tempLock = new File(
                    Maze.getMazeTempFolder() + "lock.tmp");
            Maze gameMaze = new Maze();
            // Unlock the file
            GameFileManager.load(mazeFile, tempLock);
            ZipUtilities.unzipDirectory(tempLock,
                    new File(gameMaze.getBasePath()));
            final boolean success = tempLock.delete();
            if (!success) {
                throw new IOException("Failed to delete temporary file!");
            }
            // Set prefix handler
            gameMaze.setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            gameMaze.setSuffixHandler(new SuffixHandler());
            gameMaze = gameMaze.readMaze();
            if (gameMaze == null) {
                throw new IOException("Unknown object encountered.");
            }
            app.getMazeManager().setMaze(gameMaze);
            startW = gameMaze.getStartLevel();
            gameMaze.switchLevel(startW);
            final boolean playerExists = gameMaze.doesPlayerExist();
            if (playerExists) {
                app.getMazeManager().getMaze().setPlayerToStart();
                app.getGameManager().resetViewingWindow();
            }
            gameMaze.save();
            // Final cleanup
            app.getGameManager().stateChanged();
            AbstractMazeObject
                    .setTemplateColor(ImageColorConstants.getColorForLevel(
                            PartyManager.getParty().getDungeonLevel()));
            app.getMazeManager().setLoaded(true);
            CommonDialogs.showDialog(sg + " loaded.");
            app.getGameManager().playMaze();
            app.getMazeManager().handleDeferredSuccess(true, false, null);
        } catch (final VersionException ve) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, due to the format version being unsupported.");
            DDRemix.getApplication().getMazeManager()
                    .handleDeferredSuccess(false, true, mazeFile);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, probably due to illegal characters in the file name.");
            DDRemix.getApplication().getMazeManager()
                    .handleDeferredSuccess(false, false, null);
        } catch (final IOException ie) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " failed, due to some other type of I/O error.");
            DDRemix.getApplication().getMazeManager()
                    .handleDeferredSuccess(false, false, null);
        } catch (final Exception ex) {
            DDRemix.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
