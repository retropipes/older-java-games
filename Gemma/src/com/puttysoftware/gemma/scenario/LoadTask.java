/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.scenario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.puttysoftware.fileutils.ZipUtilities;
import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.Application;
import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.resourcemanagers.LogoManager;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.map.InvalidMapException;
import com.puttysoftware.gemma.support.map.Map;

class LoadTask extends Thread {
    // Fields
    private final String filename;
    private final JFrame loadFrame;

    // Constructors
    LoadTask(String file) {
        this.filename = file;
        this.setName("File Loader");
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
        Application app = Gemma.getApplication();
        int startW;
        String sg;
        app.getGameManager().setSavedGameFlag(true);
        sg = "Saved Game";
        try {
            File scenarioFile = new File(this.filename);
            Support.createScenario();
            Map gameMap = new Map();
            gameMap.createMaps();
            ZipUtilities.unzipDirectory(scenarioFile,
                    new File(Support.getScenario().getBasePath()));
            // Load map data
            // Set prefix handler
            gameMap.setXPrefixHandler(new PrefixHandler());
            // Set suffix handler
            gameMap.setXSuffixHandler(new SuffixHandler());
            gameMap = gameMap.readMapX();
            if (gameMap == null) {
                throw new InvalidMapException("Unknown object encountered.");
            }
            app.getScenarioManager().setMap(gameMap);
            startW = gameMap.getStartLevel();
            gameMap.switchLevel(startW);
            final boolean playerExists = gameMap.doesPlayerExist();
            if (playerExists) {
                app.getGameManager().resetViewingWindow();
            }
            // Final cleanup
            app.getGameManager().stateChanged();
            CommonDialogs.showDialog(sg + " file loaded.");
            app.getScenarioManager().handleDeferredSuccess();
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
            app.getScenarioManager().handleDeferredSuccess();
        } catch (final IOException ie) {
            CommonDialogs.showDialog(
                    "Loading the " + sg.toLowerCase() + " file failed!");
            app.getScenarioManager().handleDeferredSuccess();
        } catch (final Exception ex) {
            Gemma.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
