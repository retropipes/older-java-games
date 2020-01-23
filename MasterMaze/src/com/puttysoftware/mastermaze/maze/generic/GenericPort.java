/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericPort extends GenericInfiniteLock {
    // Fields
    private char letter;

    protected GenericPort(final GenericPlug mgk, final char newLetter) {
        super(mgk);
        this.letter = Character.toUpperCase(newLetter);
    }

    @Override
    public GenericPort clone() {
        final GenericPort copy = (GenericPort) super.clone();
        copy.letter = this.letter;
        return copy;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        String fill = "";
        if (this.isLetterVowel()) {
            fill = "an";
        } else {
            fill = "a";
        }
        MasterMaze.getApplication().showMessage(
                "You need " + fill + " " + this.letter + " plug");
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public String getName() {
        return this.letter + " Port";
    }

    @Override
    public abstract int getBaseID();

    @Override
    public String getPluralName() {
        return this.letter + " Ports";
    }

    private boolean isLetterVowel() {
        if (this.letter == 'A' || this.letter == 'E' || this.letter == 'I'
                || this.letter == 'O' || this.letter == 'U') {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_LETTER_LOCK);
        this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public String getDescription() {
        String fill;
        if (this.isLetterVowel()) {
            fill = "an";
        } else {
            fill = "a";
        }
        return this.letter + " Ports require " + fill + " " + this.letter
                + " Plug to open.";
    }
}
