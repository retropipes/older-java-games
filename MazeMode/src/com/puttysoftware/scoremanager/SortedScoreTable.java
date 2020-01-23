/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.scoremanager;

import java.io.IOException;

import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

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

    public static SortedScoreTable readSortedScoreTableX(final XDataReader xdr)
            throws IOException {
        final boolean order = xdr.readBoolean();
        final int len = xdr.readInt();
        final String unit = xdr.readString();
        final SortedScoreTable sst = new SortedScoreTable(len, order, unit);
        for (int x = 0; x < len; x++) {
            sst.table[x] = Score.readScoreX(xdr);
        }
        return sst;
    }

    public void writeSortedScoreTableX(final XDataWriter xdw)
            throws IOException {
        xdw.writeBoolean(this.sortOrder);
        xdw.writeInt(this.table.length);
        if (this.unit.length() > 1) {
            xdw.writeString(this.unit.substring(1));
        } else {
            xdw.writeString(this.unit);
        }
        for (final Score element : this.table) {
            element.writeScoreX(xdw);
        }
    }
}
