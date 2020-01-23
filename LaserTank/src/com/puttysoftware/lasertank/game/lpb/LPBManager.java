/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.game.lpb;

import java.awt.FileDialog;
import java.io.File;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.lasertank.Application;
import com.puttysoftware.lasertank.LaserTank;
import com.puttysoftware.lasertank.prefs.PreferencesManager;
import com.puttysoftware.lasertank.stringmanagers.StringConstants;
import com.puttysoftware.lasertank.stringmanagers.StringLoader;
import com.puttysoftware.lasertank.utilities.Extension;
import com.puttysoftware.xio.FilenameChecker;

public class LPBManager {
    // Constructors
    private LPBManager() {
        // Do nothing
    }

    // Methods
    public static void loadLPB() {
        final Application app = LaserTank.getApplication();
        String filename, extension, file, dir;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        final FileDialog fd = new FileDialog(app.getOutputFrame(),
                StringLoader.loadString(StringConstants.GAME_STRINGS_FILE,
                        StringConstants.GAME_STRING_LOAD_PLAYBACK),
                FileDialog.LOAD);
        fd.setDirectory(lastOpen);
        fd.setVisible(true);
        file = fd.getFile();
        dir = fd.getDirectory();
        if (file != null && dir != null) {
            filename = dir + file;
            extension = LPBManager.getExtension(filename);
            if (extension.equals(Extension.getOldPlaybackExtension())) {
                LPBManager.loadFile(filename);
            } else {
                CommonDialogs.showDialog(StringLoader.loadString(
                        StringConstants.DIALOG_STRINGS_FILE,
                        StringConstants.DIALOG_STRING_NON_PLAYBACK_FILE));
            }
        }
    }

    public static void loadFile(final String filename) {
        if (!FilenameChecker.isFilenameOK(LPBManager.getNameWithoutExtension(
                LPBManager.getFileNameOnly(filename)))) {
            CommonDialogs.showErrorDialog(
                    StringLoader.loadString(StringConstants.DIALOG_STRINGS_FILE,
                            StringConstants.DIALOG_STRING_ILLEGAL_CHARACTERS),
                    StringLoader.loadString(StringConstants.DIALOG_STRINGS_FILE,
                            StringConstants.DIALOG_STRING_LOAD));
        } else {
            final LPBLoadTask lpblt = new LPBLoadTask(filename);
            lpblt.start();
        }
    }

    private static String getExtension(final String s) {
        String ext = null;
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
