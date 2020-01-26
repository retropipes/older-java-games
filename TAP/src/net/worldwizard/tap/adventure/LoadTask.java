/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.tap.adventure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.worldwizard.tap.Application;
import net.worldwizard.tap.Messager;
import net.worldwizard.tap.TAP;

class LoadTask extends Thread {
    // Fields
    private Adventure gameAdventure;
    private final String filename;

    // Constructors
    LoadTask(final String file) {
        this.filename = file;
        this.setName("File Loader");
    }

    // Methods
    @Override
    public void run() {
        final Application app = TAP.getApplication();
        final String sg = "Adventure";
        try {
            final File adventureFile = new File(this.filename);
            this.gameAdventure = new Adventure();
            app.getGUIManager().clearCommandOutput();
            this.gameAdventure.loadAdventure(adventureFile);
            app.getAdventureManager().setAdventure(this.gameAdventure);
            app.getAdventureManager().handleDeferredSuccess(true);
        } catch (final FileNotFoundException fnfe) {
            Messager.showMessage("Loading the " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
            app.getAdventureManager().handleDeferredSuccess(false);
        } catch (final IOException ie) {
            Messager.showMessage("Loading the " + sg.toLowerCase()
                    + " file failed, because " + ie.getMessage());
            app.getAdventureManager().handleDeferredSuccess(false);
        } catch (final Exception ex) {
            TAP.getDebug().debug(ex);
        }
    }
}
