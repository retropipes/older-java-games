/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.scoremanager;

import java.io.IOException;

import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

/**
 *
 * @author wrldwzrd89
 */
public class SavedScoreManager extends ScoreManager {
    // Fields
    private final String scoresFilename;

    // Constructors
    public SavedScoreManager(final int length, final boolean sortOrder,
            final long startingScore, final boolean showFailedMessage,
            final String customTitle, final String customUnit,
            final String scoresFile) {
        super(length, sortOrder, startingScore, showFailedMessage, customTitle,
                customUnit);
        this.scoresFilename = scoresFile;
        try {
            this.readScoresFileX();
        } catch (final IOException io) {
            // Do nothing
        }
    }

    // Methods
    @Override
    public boolean addScore(final long newScore) {
        final boolean success = super.addScore(newScore);
        try {
            this.writeScoresFileX();
        } catch (final IOException io) {
            // Do nothing
        }
        return success;
    }

    @Override
    public boolean addScore(final long newScore, final String newName) {
        final boolean success = super.addScore(newScore, newName);
        try {
            this.writeScoresFileX();
        } catch (final IOException io) {
            // Do nothing
        }
        return success;
    }

    private void readScoresFileX() throws IOException {
        final XDataReader xdr = new XDataReader(this.scoresFilename, "scores");
        this.table = SortedScoreTable.readSortedScoreTableX(xdr);
        xdr.close();
    }

    private void writeScoresFileX() throws IOException {
        final XDataWriter xdw = new XDataWriter(this.scoresFilename, "scores");
        this.table.writeSortedScoreTableX(xdw);
        xdw.close();
    }
}
