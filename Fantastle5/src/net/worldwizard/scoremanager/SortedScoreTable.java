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
import java.io.IOException;

public class SortedScoreTable extends ScoreTable {
    // Fields
    protected boolean sortOrder;

    // Constructors
    public SortedScoreTable() {
        super();
        this.sortOrder = true;
    }

    private SortedScoreTable(final int length, final boolean ascending,
            final String customUnit) {
        super(length, customUnit);
        this.sortOrder = ascending;
    }

    public SortedScoreTable(final int length, final boolean ascending,
            final long startingScore, final String customUnit) {
        super(length, customUnit);
        this.sortOrder = ascending;
        int x;
        for (x = 0; x < this.table.length; x++) {
            this.table[x].setScore(startingScore);
        }
    }

    @Override
    public void setEntryScore(final int pos, final long newScore) {
        // Do nothing
    }

    @Override
    public void setEntryName(final int pos, final String newName) {
        // Do nothing
    }

    public void addScore(final long newScore, final String newName) {
        int x, y;
        final Score newEntry = new Score(newScore, newName);
        if (this.sortOrder) {
            for (x = 0; x < this.table.length; x++) {
                if (newScore > this.table[x].getScore()) {
                    break;
                }
            }
            if (x == this.table.length) {
                return;
            }
            for (y = this.table.length - 1; y > x; y--) {
                this.table[y] = this.table[y - 1];
            }
            this.table[x] = newEntry;
        } else {
            for (x = 0; x < this.table.length; x++) {
                if (newScore < this.table[x].getScore()) {
                    break;
                }
            }
            if (x == this.table.length) {
                return;
            }
            for (y = this.table.length - 1; y > x; y--) {
                this.table[y] = this.table[y - 1];
            }
            this.table[x] = newEntry;
        }
    }

    public boolean checkScore(final long newScore) {
        int x;
        if (this.sortOrder) {
            for (x = 0; x < this.table.length; x++) {
                if (newScore > this.table[x].getScore()) {
                    x--;
                    break;
                }
            }
            if (x == this.table.length) {
                return false;
            }
        } else {
            for (x = 0; x < this.table.length; x++) {
                if (newScore < this.table[x].getScore()) {
                    x--;
                    break;
                }
            }
            if (x == this.table.length) {
                return false;
            }
        }
        return true;
    }

    public static SortedScoreTable readSortedScoreTable(
            final BufferedReader stream) throws IOException {
        final boolean order = Boolean.parseBoolean(stream.readLine());
        final int len = Integer.parseInt(stream.readLine());
        final String unit = stream.readLine();
        final SortedScoreTable sst = new SortedScoreTable(len, order, unit);
        for (int x = 0; x < len; x++) {
            sst.table[x] = Score.readScore(stream);
        }
        return sst;
    }

    public void writeSortedScoreTable(final BufferedWriter stream)
            throws IOException {
        stream.write(Boolean.toString(this.sortOrder) + "\n");
        stream.write(Integer.toString(this.table.length) + "\n");
        if (this.unit.length() > 1) {
            stream.write(this.unit.substring(1) + "\n");
        } else {
            stream.write(this.unit + "\n");
        }
        for (final Score element : this.table) {
            element.writeScore(stream);
        }
    }
}
