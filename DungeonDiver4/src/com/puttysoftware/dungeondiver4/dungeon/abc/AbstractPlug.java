/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;

public abstract class AbstractPlug extends AbstractInfiniteKey {
    // Fields
    private char letter;

    protected AbstractPlug(final char newLetter) {
        super();
        this.letter = Character.toUpperCase(newLetter);
    }

    @Override
    public AbstractPlug clone() {
        AbstractPlug copy = (AbstractPlug) super.clone();
        copy.letter = this.letter;
        return copy;
    }

    @Override
    public String getName() {
        return this.letter + " Plug";
    }

    @Override
    public abstract int getBaseID();

    @Override
    public String getPluralName() {
        return this.letter + " Plugs";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_LETTER_KEY);
        this.type.set(TypeConstants.TYPE_INFINITE_KEY);
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public String getDescription() {
        return this.letter + " Plugs open " + this.letter
                + " Ports, and can be used infinitely many times.";
    }
}