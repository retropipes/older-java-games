/*  LaserTank: An Arena-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: lasertank@worldwizard.net
 */
package com.puttysoftware.scoremanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

    private SortedScoreTable(int mv, int length, boolean ascending,
            String[] customUnit) {
        super(mv, length, customUnit);
        this.sortOrder = ascending;
    }

    public SortedScoreTable(int mv, int length, boolean ascending,
            long startingScore, String[] customUnit) {
        super(mv, length, customUnit);
        this.sortOrder = ascending;
        int x, y;
        for (x = 0; x < this.table.size(); x++) {
            for (y = 0; y < this.scoreCount; y++) {
                this.table.get(x).setScore(y, startingScore);
            }
        }
    }

    @Override
    public void setEntryScore(int pos, long newScore) {
        // Do nothing
    }

    @Override
    public void setEntryName(int pos, String newName) {
        // Do nothing
    }

    public void addScore(long newScore, String newName) {
        Score newEntry = new Score(newScore, newName);
        if (this.sortOrder) {
            // Append the new score to the end
            this.table.add(newEntry);
            // Sort the score table
            Collections.sort(this.table, new Score.ScoreComparatorDesc());
            // Remove the lowest score
            this.table.remove(0);
        } else {
            // Append the new score to the end
            this.table.add(newEntry);
            // Sort the score table
            Collections.sort(this.table, new Score.ScoreComparatorAsc());
            // Remove the highest score
            this.table.remove(this.table.size() - 1);
        }
    }

    public void addScore(long[] newScore, String newName) {
        Score newEntry = new Score(this.scoreCount, newScore, newName);
        if (this.sortOrder) {
            // Append the new score to the end
            this.table.add(newEntry);
            // Sort the score table
            Collections.sort(this.table, new Score.ScoreComparatorDesc());
            // Remove the lowest score
            this.table.remove(this.table.size() - 1);
        } else {
            // Append the new score to the end
            this.table.add(newEntry);
            // Sort the score table
            Collections.sort(this.table, new Score.ScoreComparatorAsc());
            // Remove the highest score
            this.table.remove(this.table.size() - 1);
        }
    }

    public boolean checkScore(long[] newScore) {
        Score newEntry = new Score(this.scoreCount, newScore, "");
        ArrayList<Score> temp = new ArrayList<>(this.table);
        if (this.sortOrder) {
            // Copy the current table to the temporary table
            Collections.copy(temp, this.table);
            // Append the new score to the end
            temp.add(newEntry);
            // Sort the score table
            Collections.sort(temp, new Score.ScoreComparatorDesc());
            // Determine if lowest score would be removed
            return !Collections.min(temp, new Score.ScoreComparatorDesc())
                    .equals(newEntry);
        } else {
            // Copy the current table to the temporary table
            Collections.copy(temp, this.table);
            // Append the new score to the end
            temp.add(newEntry);
            // Sort the score table
            Collections.sort(temp, new Score.ScoreComparatorAsc());
            // Determine if highest score would be removed
            return !Collections.max(temp, new Score.ScoreComparatorAsc())
                    .equals(newEntry);
        }
    }

    public static SortedScoreTable readSortedScoreTable(XDataReader xdr)
            throws IOException {
        boolean order = xdr.readBoolean();
        int len = xdr.readInt();
        int unitLen = xdr.readInt();
        String[] unitArr = new String[unitLen];
        for (int z = 0; z < unitLen; z++) {
            unitArr[z] = xdr.readString();
        }
        SortedScoreTable sst = new SortedScoreTable(unitLen, len, order,
                unitArr);
        for (int x = 0; x < len; x++) {
            sst.table.set(x, Score.readScore(xdr));
        }
        return sst;
    }

    public void writeSortedScoreTable(XDataWriter xdw) throws IOException {
        xdw.writeBoolean(this.sortOrder);
        xdw.writeInt(this.table.size());
        xdw.writeInt(this.unit.length);
        for (int z = 0; z < this.unit.length; z++) {
            if (this.unit[z].length() > 1) {
                xdw.writeString(this.unit[z].substring(1));
            } else {
                xdw.writeString(this.unit[z]);
            }
        }
        for (int x = 0; x < this.table.size(); x++) {
            this.table.get(x).writeScore(xdw);
        }
    }
}
