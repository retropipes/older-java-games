/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractPort extends AbstractInfiniteLock {
    // Fields
    private char letter;

    protected AbstractPort(final AbstractPlug mgk, final char newLetter) {
        super(mgk);
        this.letter = Character.toUpperCase(newLetter);
    }

    @Override
    public AbstractPort clone() {
        final AbstractPort copy = (AbstractPort) super.clone();
        copy.letter = this.letter;
        return copy;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        String fill = "";
        if (this.isLetterVowel()) {
            fill = "an";
        } else {
            fill = "a";
        }
        DungeonDiver4.getApplication()
                .showMessage("You need " + fill + " " + this.letter + " plug");
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
