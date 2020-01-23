/*  LaserTank: An Arena-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: lasertank@worldwizard.net
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
    private String scoresFilename;

    // Constructors
    public SavedScoreManager(int mv, int length, boolean sortOrder,
            long startingScore, String customTitle, String[] customUnit,
            String scoresFile) {
        super(mv, length, sortOrder, startingScore, customTitle, customUnit);
        this.scoresFilename = scoresFile;
        try {
            this.readScoresFile();
        } catch (IOException io) {
            // Do nothing
        }
    }

    // Methods
    @Override
    public boolean addScore(long newScore) {
        boolean success = super.addScore(newScore);
        try {
            this.writeScoresFile();
        } catch (IOException io) {
            // Do nothing
        }
        return success;
    }

    @Override
    public boolean addScore(long[] newScore) {
        boolean success = super.addScore(newScore);
        try {
            this.writeScoresFile();
        } catch (IOException io) {
            // Do nothing
        }
        return success;
    }

    @Override
    public boolean addScore(long newScore, String newName) {
        boolean success = super.addScore(newScore, newName);
        try {
            this.writeScoresFile();
        } catch (IOException io) {
            // Do nothing
        }
        return success;
    }

    private void readScoresFile() throws IOException {
        try (XDataReader xdr = new XDataReader(this.scoresFilename, "scores")) {
            this.table = SortedScoreTable.readSortedScoreTable(xdr);
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private void writeScoresFile() throws IOException {
        try (XDataWriter xdw = new XDataWriter(this.scoresFilename, "scores")) {
            this.table.writeSortedScoreTable(xdw);
        } catch (IOException ioe) {
            throw ioe;
        }
    }
}
