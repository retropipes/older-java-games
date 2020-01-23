/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.scoremanager;

import java.io.IOException;

import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

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
            this.readScoresFileXML();
        } catch (final IOException io) {
            // Do nothing
        }
    }

    // Methods
    @Override
    public boolean addScore(final long newScore) {
        final boolean success = super.addScore(newScore);
        try {
            this.writeScoresFileXML();
        } catch (final IOException io) {
            // Do nothing
        }
        return success;
    }

    @Override
    public boolean addScore(final long newScore, final String newName) {
        final boolean success = super.addScore(newScore, newName);
        try {
            this.writeScoresFileXML();
        } catch (final IOException io) {
            // Do nothing
        }
        return success;
    }

    private void readScoresFileXML() throws IOException {
        final XMLDataReader xdr = new XMLDataReader(this.scoresFilename,
                "scores");
        this.table = SortedScoreTable.readSortedScoreTableXML(xdr);
        xdr.close();
    }

    private void writeScoresFileXML() throws IOException {
        final XMLDataWriter xdw = new XMLDataWriter(this.scoresFilename,
                "scores");
        this.table.writeSortedScoreTableXML(xdw);
        xdw.close();
    }
}
