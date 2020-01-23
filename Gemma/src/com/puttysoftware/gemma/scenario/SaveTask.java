/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.scenario;

import java.io.File;
import java.io.FileNotFoundException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fileutils.ZipUtilities;
import com.puttysoftware.gemma.Application;
import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.scenario.Extension;

class SaveTask extends Thread {
    // Fields
    private String filename;

    // Constructors
    SaveTask(String file) {
        this.filename = file;
        this.setName("Saved Game File Writer");
    }

    @Override
    public void run() {
        Application app = Gemma.getApplication();
        final String sg = "Saved Game";
        // filename check
        boolean hasExtension = SaveTask.hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += Extension.getGameExtensionWithPeriod();
        }
        File mapFile = new File(this.filename);
        try {
            // Set prefix handler
            app.getScenarioManager().getMap()
                    .setXPrefixHandler(new PrefixHandler());
            // Set suffix handler
            app.getScenarioManager().getMap()
                    .setXSuffixHandler(new SuffixHandler());
            app.getScenarioManager().getMap().writeMapX();
            ZipUtilities.zipDirectory(
                    new File(Support.getScenario().getBasePath()), mapFile);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Writing the " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
        } catch (final Exception ex) {
            Gemma.getErrorLogger().logError(ex);
        }
        Gemma.getApplication().showMessage(sg + " file saved.");
        app.getScenarioManager().handleDeferredSuccess();
    }

    private static boolean hasExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(i + 1).toLowerCase();
        }
        if (ext == null) {
            return false;
        } else {
            return true;
        }
    }
}
