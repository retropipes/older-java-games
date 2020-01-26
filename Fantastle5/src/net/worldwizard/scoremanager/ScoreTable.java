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

public class ScoreTable {
    // Fields and Constants
    protected Score[] table;
    protected String unit;
    protected static final String DEFAULT_UNIT = "";

    // Constructors
    public ScoreTable() {
        this.table = new Score[10];
        int x;
        for (x = 0; x < 10; x++) {
            this.table[x] = new Score();
        }
        this.unit = ScoreTable.DEFAULT_UNIT;
    }

    public ScoreTable(final int length, final String customUnit) {
        this.table = new Score[length];
        int x;
        for (x = 0; x < length; x++) {
            this.table[x] = new Score();
        }
        if (customUnit == null || customUnit.equals("")) {
            this.unit = ScoreTable.DEFAULT_UNIT;
        } else {
            this.unit = " " + customUnit;
        }
    }

    // Methods
    public long getEntryScore(final int pos) {
        return this.table[pos].getScore();
    }

    public String getEntryName(final int pos) {
        return this.table[pos].getName();
    }

    public int getLength() {
        return this.table.length;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setEntryScore(final int pos, final long newScore) {
        this.table[pos].setScore(newScore);
    }

    public void setEntryName(final int pos, final String newName) {
        this.table[pos].setName(newName);
    }

    public static ScoreTable readScoreTable(final BufferedReader stream)
            throws IOException {
        final int len = Integer.parseInt(stream.readLine());
        final String unit = stream.readLine();
        final ScoreTable st = new ScoreTable(len, unit);
        for (int x = 0; x < len; x++) {
            st.table[x] = Score.readScore(stream);
        }
        return st;
    }

    public void writeScoreTable(final BufferedWriter stream)
            throws IOException {
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
