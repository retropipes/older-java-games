/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.tap.adventure;

import java.awt.FileDialog;
import java.io.File;

import net.worldwizard.tap.Application;
import net.worldwizard.tap.Messager;
import net.worldwizard.tap.TAP;

public class AdventureManager {
    // Fields
    private Adventure currentAdventure;
    private boolean loaded;

    // Constructors
    public AdventureManager() {
        this.loaded = false;
    }

    // Methods
    public Adventure getAdventure() {
        return this.currentAdventure;
    }

    public void setAdventure(final Adventure newAdv) {
        this.currentAdventure = newAdv;
    }

    protected void handleDeferredSuccess(final boolean value) {
        this.setLoaded(value);
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        final Application app = TAP.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public void loadFromOSHandler(final String infilename) {
        if (!this.loaded) {
            String extension;
            final File file = new File(infilename);
            final String filename = file.getAbsolutePath();
            extension = AdventureManager.getExtension(file);
            if (extension.equals(Extension.getAdventureExtension())) {
                AdventureManager.loadFile(filename);
            }
        }
    }

    public boolean loadAdventure() {
        final Application app = TAP.getApplication();
        String dirname, filename, extension;
        final FileDialog fd = new FileDialog(app.getOutputFrame(),
                "Open Adventure", FileDialog.LOAD);
        fd.setVisible(true);
        filename = fd.getFile();
        dirname = fd.getDirectory();
        if (filename != null) {
            final File file = new File(dirname + filename);
            extension = AdventureManager.getExtension(file);
            if (extension.equals(Extension.getAdventureExtension())) {
                AdventureManager.loadFile(dirname + filename);
            } else {
                Messager.showMessage(
                        "You opened something other than an adventure file. Select an adventure file, and try again.");
            }
        } else {
            // User cancelled
            if (this.loaded) {
                return true;
            }
        }
        return false;
    }

    private static void loadFile(final String filename) {
        if (!FilenameChecker
                .isFilenameOK(AdventureManager.getNameWithoutExtension(
                        AdventureManager.getFileNameOnly(filename)))) {
            Messager.showMessage(
                    "The file you selected contains illegal characters in its"
                            + "name. These characters are not allowed: /?<>\\:|\""
                            + "Files named con, nul, or prn are illegal, as are files"
                            + "named com1 through com9 and lpt1 through lpt9.");
        } else {
            final LoadTask lt = new LoadTask(filename);
            lt.start();
        }
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getNameWithoutExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getFileNameOnly(final String s) {
        String fno = null;
        final int i = s.lastIndexOf(File.separatorChar);
        if (i > 0 && i < s.length() - 1) {
            fno = s.substring(i + 1);
        } else {
            fno = s;
        }
        return fno;
    }
}
