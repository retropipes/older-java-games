/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.game;

import java.io.File;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazer5d.files.xml.XMLExtension;
import com.puttysoftware.scoremanager.SavedScoreManager;
import com.puttysoftware.scoremanager.ScoreManager;

public class ScoreTracker {
    // Fields
    private String scoresFile;
    private SavedScoreManager ssMgr;
    private long score;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Mazer5D/ScoresV5/";
    private static final String WIN_DIR = "\\Mazer5D\\ScoresV5\\";
    private static final String UNIX_DIR = "/.mazer5d/scoresv5/";

    // Constructors
    public ScoreTracker() {
        this.scoresFile = "";
        this.score = 0L;
        this.ssMgr = null;
    }

    // Methods
    public boolean checkScore() {
        return this.ssMgr.checkScore(new long[] { this.score });
    }

    public void commitScore() {
        final boolean result = this.ssMgr.addScore(this.score);
        if (result) {
            this.ssMgr.viewTable();
        }
    }

    public void resetScore() {
        this.score = 0L;
    }

    public void resetScore(final String filename) {
        this.setScoreFile(filename);
        this.score = 0L;
    }

    public void setScoreFile(final String filename) {
        // Check filename argument
        if (filename != null) {
            if (filename.equals("")) {
                throw new IllegalArgumentException("Filename cannot be empty!");
            }
        } else {
            throw new IllegalArgumentException("Filename cannot be null!");
        }
        // Make sure the needed directories exist first
        final File sf = ScoreTracker.getScoresFile(filename);
        final File parent = new File(sf.getParent());
        if (!parent.exists()) {
            final boolean success = parent.mkdirs();
            if (!success) {
                throw new RuntimeException(
                        "Couldn't make directories for scores file!");
            }
        }
        this.scoresFile = sf.getAbsolutePath();
        this.ssMgr = new SavedScoreManager(1, 10,
                ScoreManager.SORT_ORDER_ASCENDING, 0L, "Mazer5D High Scores",
                new String[] { "points" }, this.scoresFile);
    }

    public void addToScore(final long value) {
        this.score += value;
    }

    public long getScore() {
        return this.score;
    }

    public void setScore(final long newScore) {
        this.score = newScore;
    }

    public void showCurrentScore() {
        CommonDialogs
                .showDialog("Your current score: " + this.score + " points");
    }

    public void showScoreTable() {
        this.ssMgr.viewTable();
    }

    private static String getScoreDirPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(ScoreTracker.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(ScoreTracker.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(ScoreTracker.UNIX_PREFIX);
        }
    }

    private static String getScoreDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return ScoreTracker.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return ScoreTracker.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return ScoreTracker.UNIX_DIR;
        }
    }

    private static File getScoresFile(final String filename) {
        final StringBuilder b = new StringBuilder();
        b.append(ScoreTracker.getScoreDirPrefix());
        b.append(ScoreTracker.getScoreDirectory());
        b.append(filename);
        b.append(XMLExtension.getXMLScoresExtensionWithPeriod());
        return new File(b.toString());
    }
}
