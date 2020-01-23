/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.battle;

public class VictorySpoilsDescription {
    // Fields
    private final long[] expPerMonster;
    private int goldWon;

    // Constructor
    public VictorySpoilsDescription(int monsterCount) {
        this.expPerMonster = new long[monsterCount];
        this.goldWon = 0;
    }

    // Methods
    public long getExpPerMonster(int index) {
        return this.expPerMonster[index];
    }

    public long getTotalExp() {
        long total = 0;
        for (int x = 0; x < this.expPerMonster.length; x++) {
            total += this.expPerMonster[x];
        }
        return total;
    }

    public void setExpPerMonster(int index, long value) {
        this.expPerMonster[index] = value;
    }

    public int getGoldWon() {
        return this.goldWon;
    }

    public void setGoldWon(int value) {
        this.goldWon = value;
    }

    public int getMonsterCount() {
        return this.expPerMonster.length;
    }
}
