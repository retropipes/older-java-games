/*  Fantastle: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
package net.worldwizard.scoremanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author wrldwzrd89
 */
public class SavedScoreManager extends ScoreManager {
    // Fields
    private final String scoresFilename;

    // Constructors
    public SavedScoreManager(final String scoresFile) {
        super();
        this.scoresFilename = scoresFile;
        try {
            this.readScoresFile();
        } catch (final IOException io) {
            // Do nothing
        }
    }

    public SavedScoreManager(final int length, final boolean sortOrder,
            final long startingScore, final boolean showFailedMessage,
            final String customTitle, final String customUnit,
            final String scoresFile) {
        super(length, sortOrder, startingScore, showFailedMessage, customTitle,
                customUnit);
        this.scoresFilename = scoresFile;
        try {
            this.readScoresFile();
        } catch (final IOException io) {
            // Do nothing
        }
    }

    // Methods
    @Override
    public boolean addScore(final long newScore) {
        final boolean success = super.addScore(newScore);
        try {
            this.writeScoresFile();
        } catch (final IOException io) {
            // Do nothing
        }
        return success;
    }

    @Override
    public boolean addScore(final long newScore, final String newName) {
        final boolean success = super.addScore(newScore, newName);
        try {
            this.writeScoresFile();
        } catch (final IOException io) {
            // Do nothing
        }
        return success;
    }

    private void readScoresFile() throws IOException {
        try (final BufferedReader br = new BufferedReader(new FileReader(
                this.scoresFilename))) {
            this.table = SortedScoreTable.readSortedScoreTable(br);
        }
    }

    private void writeScoresFile() throws IOException {
        try (final BufferedWriter bw = new BufferedWriter(new FileWriter(
                this.scoresFilename))) {
            this.table.writeSortedScoreTable(bw);
        }
    }
}
