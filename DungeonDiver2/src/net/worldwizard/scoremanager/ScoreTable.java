/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package net.worldwizard.scoremanager;

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
}
