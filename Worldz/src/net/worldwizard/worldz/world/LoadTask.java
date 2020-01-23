/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.worldwizard.io.DirectoryUtilities;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;

public class LoadTask extends Thread {
    // Fields
    private World gameWorld;
    private final String filename;
    private final boolean isSavedGame;

    // Constructors
    public LoadTask(final String file, final boolean saved) {
        this.filename = file;
        this.isSavedGame = saved;
        this.setName("File Reader");
    }

    // Methods
    @Override
    public void run() {
        final Application app = Worldz.getApplication();
        int startW;
        String sg;
        if (this.isSavedGame) {
            app.getGameManager().setSavedGameFlag(true);
            sg = "Saved Game";
        } else {
            app.getGameManager().setSavedGameFlag(false);
            sg = "World";
        }
        try {
            final File worldFile = new File(this.filename);
            try {
                this.gameWorld = new World();
                DirectoryUtilities.unzipDirectory(worldFile, new File(
                        this.gameWorld.getBasePath()));
                // Set prefix handler
                this.gameWorld.setPrefixHandler(new PrefixHandler());
                // Set suffix handler
                if (this.isSavedGame) {
                    this.gameWorld.setSuffixHandler(new SuffixHandler());
                } else {
                    this.gameWorld.setSuffixHandler(null);
                }
                this.gameWorld = this.gameWorld.readWorld();
                if (this.gameWorld == null) {
                    throw new InvalidWorldException(
                            "Unknown object encountered.");
                }
                app.getWorldManager().setWorld(this.gameWorld);
                startW = this.gameWorld.getStartLevel();
                this.gameWorld.switchLevel(startW);
                final boolean playerExists = this.gameWorld.doesPlayerExist();
                if (playerExists) {
                    app.getGameManager()
                            .getPlayerManager()
                            .setPlayerLocation(this.gameWorld.getStartColumn(),
                                    this.gameWorld.getStartRow(),
                                    this.gameWorld.getStartFloor(), startW);
                    app.getGameManager().resetViewingWindow();
                }
                if (!this.isSavedGame) {
                    this.gameWorld.save();
                }
                app.getWorldManager().setWorld(this.gameWorld);
                // Final cleanup
                final String lum = app.getWorldManager().getLastUsedWorld();
                final String lug = app.getWorldManager().getLastUsedGame();
                app.getWorldManager().clearLastUsedFilenames();
                if (this.isSavedGame) {
                    app.getWorldManager().setLastUsedGame(lug);
                } else {
                    app.getWorldManager().setLastUsedWorld(lum);
                }
                app.getEditor().worldChanged();
                app.getGameManager().stateChanged();
                Messager.showDialog(sg + " file loaded.");
                app.getWorldManager().handleDeferredSuccess(true);
            } catch (final FileNotFoundException fnfe) {
                Messager.showDialog("Reading the "
                        + sg.toLowerCase()
                        + " file failed, probably due to illegal characters in the file name.");
                app.getWorldManager().handleDeferredSuccess(false);
            } catch (final IOException ie) {
                throw new InvalidWorldException("Error reading "
                        + sg.toLowerCase() + " file.");
            }
        } catch (final InvalidWorldException ime) {
            Messager.showDialog(ime.getMessage());
            app.getWorldManager().handleDeferredSuccess(false);
        } catch (final Exception ex) {
            Worldz.getDebug().debug(ex);
        }
    }
}
