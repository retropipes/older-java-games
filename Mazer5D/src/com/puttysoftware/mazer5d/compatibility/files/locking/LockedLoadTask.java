/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.files.locking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fileutils.ZipUtilities;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.LogoImageIndex;
import com.puttysoftware.mazer5d.compatibility.files.InvalidMazeException;
import com.puttysoftware.mazer5d.compatibility.files.xml.XMLPrefixHandler;
import com.puttysoftware.mazer5d.compatibility.maze.MazeModel;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.LogoImageLoader;

public class LockedLoadTask extends Thread {
    // Fields
    private MazeModel gameMaze;
    private final String filename;
    private final JFrame loadFrame;
    private final JProgressBar loadBar;

    // Constructors
    public LockedLoadTask(final String file) {
        this.filename = file;
        this.setName("Locked File Loader");
        this.loadFrame = new JFrame("Loading...");
        this.loadFrame.setIconImage(LogoImageLoader.load(
                LogoImageIndex.MICRO_LOGO));
        this.loadBar = new JProgressBar();
        this.loadBar.setIndeterminate(true);
        this.loadFrame.getContentPane().add(this.loadBar);
        this.loadFrame.setResizable(false);
        this.loadFrame.setDefaultCloseOperation(
                WindowConstants.DO_NOTHING_ON_CLOSE);
        this.loadFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        this.loadFrame.setVisible(true);
        final BagOStuff app = Mazer5D.getBagOStuff();
        int startW;
        String sg;
        app.getGameManager().setSavedGameFlag(false);
        sg = "Maze";
        try {
            final File mazeFile = new File(this.filename);
            final File tempLock = new File(MazeModel.getMazeTempFolder()
                    + "lock.tmp");
            try {
                this.gameMaze = new MazeModel();
                // Unlock the file
                LockedWrapper.unlock(mazeFile, tempLock);
                ZipUtilities.unzipDirectory(tempLock, new File(this.gameMaze
                        .getBasePath()));
                tempLock.delete();
                // Set prefix handler
                this.gameMaze.setXMLPrefixHandler(new XMLPrefixHandler());
                // Set suffix handler
                this.gameMaze.setXMLSuffixHandler(null);
                this.gameMaze = this.gameMaze.readMazeXML();
                if (this.gameMaze == null) {
                    throw new InvalidMazeException(
                            "Unknown object encountered.");
                }
                app.getMazeManager().setMaze(this.gameMaze);
                startW = this.gameMaze.getStartLevel();
                this.gameMaze.switchLevel(startW);
                final boolean playerExists = this.gameMaze.doesPlayerExist();
                if (playerExists) {
                    app.getGameManager().getPlayerManager().setPlayerLocation(
                            this.gameMaze.getStartColumn(), this.gameMaze
                                    .getStartRow(), this.gameMaze
                                            .getStartFloor(), startW);
                    app.getGameManager().resetViewingWindow();
                }
                this.gameMaze.save();
                // Final cleanup
                app.getMenuManager().setLockedFlag();
                app.getEditor().mazeChanged();
                app.getGameManager().stateChanged();
                CommonDialogs.showDialog("Locked " + sg + " file loaded.");
                app.getMazeManager().handleDeferredSuccess(true);
            } catch (final FileNotFoundException fnfe) {
                CommonDialogs.showDialog("Loading the locked " + sg
                        .toLowerCase()
                        + " file failed, probably due to illegal characters in the file name.");
                app.getMazeManager().handleDeferredSuccess(false);
            } catch (final IOException ie) {
                ie.printStackTrace();
                throw new InvalidMazeException("Error loading locked " + sg
                        .toLowerCase() + " file.");
            }
        } catch (final InvalidMazeException ime) {
            CommonDialogs.showDialog(ime.getMessage());
            app.getMazeManager().handleDeferredSuccess(false);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
