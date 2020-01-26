/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.battle;

public class VictorySpoilsDescription {
    // Fields
    private final long[] expPerMonster;
    private int goldWon;

    // Constructor
    public VictorySpoilsDescription(final int monsterCount) {
        this.expPerMonster = new long[monsterCount];
        this.goldWon = 0;
    }

    // Methods
    public long getExpPerMonster(final int index) {
        return this.expPerMonster[index];
    }

    public long getTotalExp() {
        long total = 0;
        for (final long element : this.expPerMonster) {
            total += element;
        }
        return total;
    }

    public void setExpPerMonster(final int index, final long value) {
        this.expPerMonster[index] = value;
    }

    public int getGoldWon() {
        return this.goldWon;
    }

    public void setGoldWon(final int value) {
        this.goldWon = value;
    }

    public int getMonsterCount() {
        return this.expPerMonster.length;
    }
}
