/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.variables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.dungeondiver2.Application;
import net.worldwizard.dungeondiver2.DungeonDiverII;
import net.worldwizard.dungeondiver2.resourcemanagers.LogoManager;
import net.worldwizard.support.Support;
import net.worldwizard.support.map.InvalidMapException;
import net.worldwizard.support.map.Map;
import net.worldwizard.xio.ZipUtilities;

public class LoadTask extends Thread {
    // Fields
    private Map gameMap;
    private final String filename;
    private final boolean isSavedGame;
    private final JFrame loadFrame;
    private final JProgressBar loadBar;

    // Constructors
    public LoadTask(final String file, final boolean saved) {
        this.filename = file;
        this.isSavedGame = saved;
        this.setName("Saved Game File Loader");
        this.loadFrame = new JFrame("Loading...");
        this.loadFrame.setIconImage(LogoManager.getIconLogo());
        this.loadBar = new JProgressBar();
        this.loadBar.setIndeterminate(true);
        this.loadFrame.getContentPane().add(this.loadBar);
        this.loadFrame.setResizable(false);
        this.loadFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.loadFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        this.loadFrame.setVisible(true);
        final Application app = DungeonDiverII.getApplication();
        int startW;
        String sg;
        if (this.isSavedGame) {
            app.getGameManager().setSavedGameFlag(true);
            sg = "Saved Game";
        } else {
            app.getGameManager().setSavedGameFlag(false);
            sg = "Variables";
        }
        try {
            final File variablesFile = new File(this.filename);
            try {
                Support.createVariables();
                ZipUtilities.unzipDirectory(variablesFile,
                        new File(Support.getVariables().getBasePath()));
                // Load variables data
                Support.getVariables().read();
                // Load map data
                this.gameMap = new Map();
                if (this.gameMap.doMapsExist()) {
                    // Set prefix handler
                    this.gameMap.setXPrefixHandler(new PrefixHandler());
                    // Set suffix handler
                    if (this.isSavedGame) {
                        this.gameMap.setXSuffixHandler(new SuffixHandler());
                    } else {
                        this.gameMap.setXSuffixHandler(null);
                    }
                    this.gameMap = this.gameMap.readMapX();
                    if (this.gameMap == null) {
                        throw new InvalidMapException(
                                "Unknown object encountered.");
                    }
                    app.getVariablesManager().setMap(this.gameMap);
                    startW = this.gameMap.getStartLevel();
                    this.gameMap.switchLevel(startW);
                    final boolean playerExists = this.gameMap.doesPlayerExist();
                    if (playerExists) {
                        app.getGameManager().getPlayerManager()
                                .setPlayerLocation(
                                        this.gameMap.getStartColumn(),
                                        this.gameMap.getStartRow(),
                                        this.gameMap.getStartFloor(), startW);
                        app.getGameManager().resetViewingWindow();
                    }
                    if (!this.isSavedGame) {
                        this.gameMap.save();
                    }
                }
                // Final cleanup
                final String lum = app.getVariablesManager()
                        .getLastUsedVariables();
                final String lug = app.getVariablesManager().getLastUsedGame();
                app.getVariablesManager().clearLastUsedFilenames();
                if (this.isSavedGame) {
                    app.getVariablesManager().setLastUsedGame(lug);
                } else {
                    app.getVariablesManager().setLastUsedVariables(lum);
                }
                app.getGameManager().stateChanged();
                CommonDialogs.showDialog(sg + " file loaded.");
                app.getVariablesManager().handleDeferredSuccess();
            } catch (final FileNotFoundException fnfe) {
                CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                        + " file failed, probably due to illegal characters in the file name.");
                app.getVariablesManager().handleDeferredSuccess();
            } catch (final IOException ie) {
                throw new InvalidMapException(
                        "Error loading " + sg.toLowerCase() + " file.");
            }
        } catch (final InvalidMapException ime) {
            CommonDialogs.showDialog(ime.getMessage());
            app.getVariablesManager().handleDeferredSuccess();
        } catch (final Exception ex) {
            DungeonDiverII.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
